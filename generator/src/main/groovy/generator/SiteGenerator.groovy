/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package generator

import groovy.io.FileType
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import model.Changelog
import model.Page
import model.Section
import model.SectionItem
import model.SiteMap
import org.asciidoctor.Options
import org.asciidoctor.ast.Document

import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.WatchEvent

import static generator.DocumentationHTMLCleaner.parsePage
import static java.nio.file.StandardWatchEventKinds.*

@CompileStatic
class SiteGenerator {

    private final static Closure SEMANTIC_SORT = { String v1, String v2 ->
        List<String> items1 = decomposeVersion(v1)
        List<String> items2 = decomposeVersion(v2)
        for (int i=0; i<Math.max(items1.size(),items2.size());i++) {
            if (i>=items2.size()) {
                return 1
            }
            if (i>=items1.size()) {
                return -1
            }
            def p1 = items1[i]
            def p2 = items2[i]
            if (p1.isNumber()) {
                if (p2.isNumber()) {
                    def pi1 = p1.toInteger()
                    def pi2 = p2.toInteger()

                    if (pi1 < pi2) {
                        return 1
                    } else if (pi1 > pi2) {
                        return -1
                    }
                } else {
                    return -1
                }
            } else if (p2.isNumber()) {
                return 1
            } else {
                return p2 <=> p1
            }
        }
        0
    }

    File sourcesDir
    File outputDir
    String sitemapFilename

    private MarkupTemplateEngine tplEngine
    private SiteMap siteMap

    void setup() {

        println "Generating website using Groovy ${GroovySystem.version}"

        def tplConf = new TemplateConfiguration()
        tplConf.autoIndent = true
        tplConf.autoNewLine = true
        tplConf.baseTemplateClass = PageTemplate

        def classLoader = new URLClassLoader([sourcesDir.toURI().toURL()] as URL[], this.class.classLoader)
        tplEngine = new MarkupTemplateEngine(classLoader, tplConf, new MarkupTemplateEngine.CachingTemplateResolver())

        siteMap = SiteMap.from(new File(sourcesDir, sitemapFilename))

    }

    void render(String page, String target = null, Map model = [:], String baseDir=null) {
        model.menu = siteMap.menu
        model.currentPage = target
        target = target ?: page
        File root
        if (baseDir) {
            root = new File(outputDir, baseDir)
            model[PageTemplate.BASEDIR] = baseDir
            root.mkdirs()
        } else {
            root = outputDir
        }

        new File(root,"${target}.html").write(tplEngine.createTemplateByPath("pages/${page}.groovy").make(model).toString(), 'utf-8')
    }

    void generateSite() {
        long sd = System.currentTimeMillis()
        setup()

        List<Changelog> changelogs = []
        if (siteMap.changelogs) {
            def cacheDir = new File(new File('build'), 'cache')
            cacheDir.mkdirs()
            println "Cache directory: $cacheDir"
            changelogs = ChangelogParser.fetchReleaseNotes(cacheDir)
        }

        renderDocumentation()

        renderPages(changelogs)

        renderChangelogs(changelogs)

        if (siteMap.releaseNotes) {
            renderReleaseNotes()
        }

        if (siteMap.wiki) {
            renderWiki()
        }

        if (siteMap.blog) {
            renderBlog()
        }

        long dur = System.currentTimeMillis() - sd
        println "Generated site into $outputDir in ${dur}ms"
    }

    private List<Section> renderDocumentation() {
        siteMap.documentationSections.each { Section section ->
            section.items.each { SectionItem item ->
                if (item.generate) {
                    println "Generating documentation page [$item.name]"
                    render 'docpage', item.targetFilename, [
                            category: 'Learn',
                            title   : item.name,
                            page    : parsePage("${DocUtils.DOCS_BASEURL}/html/documentation/${item.sourceFilename}.html")]
                }
            }
        }
    }

    private List<Page> renderPages(List<Changelog> changelogs) {
        siteMap.pages.each { Page page ->
            println "Rendering individual page [$page.source]"
            if ('changelogs' == page.source) {
                page.model.versions = changelogs.groovyVersion.sort(SEMANTIC_SORT)
            }
            render page.source, page.target, page.model
        }
    }

    private List<Changelog> renderChangelogs(List<Changelog> changelogs) {
        changelogs.each {
            println "Rendering changelog for Groovy $it.groovyVersion"
            render 'changelog', "changelog-$it.groovyVersion", [groovyVersion: it.groovyVersion, issues: it.issues], 'changelogs'
        }
    }

    private void renderReleaseNotes() {
        def releaseNotesVersions = new TreeSet<String>(new Comparator<String>() {
            @Override
            int compare(final String v1, final String v2) {
                v2.toDouble() <=> v1.toDouble()
            }
        })
        new File(sourcesDir, 'releasenotes').eachFile(FileType.FILES) { File file ->
            def name = file.name.substring(0, file.name.lastIndexOf('.adoc'))
            def version = name - 'groovy-'
            releaseNotesVersions << version
            println "Rendering release notes for Groovy $version"
            render 'release-notes', name, [notes: file.getText('utf-8'), groovyVersion: version], 'releasenotes'
        }
        render 'releases', 'releases', [versions: releaseNotesVersions]
    }

    private void renderWiki() {
        def asciidoctor = AsciidoctorFactory.instance
        println "Rendering wiki"

        def wikiDir = new File(sourcesDir, "wiki")
        def gepList = [:]
        wikiDir.eachFileRecurse { f->
            if (f.name.endsWith('.adoc')) {
                def header = asciidoctor.readDocumentHeader(f)
                def bn = f.name.substring(0, f.name.lastIndexOf('.adoc'))
                def author = header.author?.fullName
                if (!author) {
                    author = header.authors*.fullName.join(', ')
                }
                println "Rendering $header.documentTitle.combined${author ? ' by ' + author : ''}"
                def relativePath = []
                def p = f.parentFile
                while (p != wikiDir) {
                    relativePath << p.name
                    p = p.parentFile
                }
                String baseDir = relativePath ? "wiki${File.separator}${relativePath.join(File.separator)}" : 'wiki'
                render 'wiki', bn, [notes:f.getText('utf-8'), header: header], baseDir
                if (f.name.startsWith('GEP-')) {
                    gepList[bn] = header.documentTitle.subtitle
                }
            }
        }
        render 'geps', "geps", [list: gepList], 'wiki'
    }

    private void renderBlog() {
        def asciidoctor = AsciidoctorFactory.instance
        asciidoctor.requireLibrary('asciidoctor-diagram')
        println "Rendering blogs"

        def blogDir = new File(sourcesDir, "blog")
        Map<String, Document> blogList = [:]
        Map<String, String> contents = [:]
        Map<String, String> baseDirs = [:]
        blogDir.eachFileRecurse { f->
            if (f.name.endsWith('.adoc')) {
                def bn = f.name.substring(0, f.name.lastIndexOf('.adoc'))
                def options = Options.builder().build()
                def doc = asciidoctor.loadFile(f, options)
                println "Rendering $bn"
                def relativePath = []
                def p = f.parentFile
                while (p != blogDir) {
                    relativePath << p.name
                    p = p.parentFile
                }
                String baseDir = relativePath ? "blog${File.separator}${relativePath.join(File.separator)}" : 'blog'
                blogList[bn] = doc
                contents[bn] = f.getText('utf-8')
                baseDirs[bn] = baseDir
            }
        }
        Map<String, Set> keywords = [:]
        blogList.each { k, v ->
            String kw = v.attributes.keywords.toString()
            keywords[k] = kw?.split(',')*.trim().toSet()
            def groovyVersionInTitle = v.structuredDoctitle.combined.findAll(/(?i)(groovy \d[.]\d+[.]\d+[-\S]*)/)
            groovyVersionInTitle?.each {keywords[k] << it }
            def groovyMinorVersionInTitle = v.structuredDoctitle.combined.findAll(/(?i)(groovy \d[.]\d+)/)
            groovyMinorVersionInTitle?.each {keywords[k] << it }
        }
        Map<String, Map<String, Integer>> related = [:].withDefault{ [:] }
        [blogList.keySet(), blogList.keySet()].eachCombination { String one, String two ->
            if (one != two) {
                related[one][two] = keywords[one].intersect(keywords[two]).size()
            }
        }
        blogList.keySet().each {bn ->
            def sorted = related[bn].findAll { it.value as int > 1 }.sort { it.value }.keySet().toList().reverse()
            render 'blog', bn, [notes:contents[bn], doc: blogList[bn], related: sorted.collectEntries{ [it, blogList[it].structuredDoctitle.combined]}], baseDirs[bn]
        }
        render 'blogs', "index", [list: blogList], 'blog'
        renderBlogFeed blogList, 'blog'
    }

    @CompileDynamic
    private void renderBlogFeed(Map<String, Document> blogList, String baseDir) {
        def sorted = blogList.sort { e -> e.value.revisionInfo.date }
        def base = "http://groovy.apache.org/$baseDir"
        def feedDir = new File(outputDir, baseDir)
        feedDir.mkdirs()
        def feedFile = new File(feedDir, 'feed.atom')
        def builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def blogs = builder.bind {
            mkp.xmlDeclaration()
            namespaces << ['':'http://www.w3.org/2005/Atom']
            feed {
                title('Groovy Blogs')
                subtitle('News and stories from the Groovy Ecosystem')
                link(href: base)
                link(href: "$base/feed.atom", rel: 'self')
                id(base)
                sorted.each { k, v ->
                    def publishDate = v.revisionInfo.date
                    def updateDate = v.attributes.updated ?: v.revisionInfo.date
                    // Atom standard is a little vague on author tag.
                    // Multiple are allowed but many atom readers just display
                    // first or last, so we'll have one combined one.
                    def authorName = null
                    if (v.authors) {
                        authorName = v.authors*.fullName.join(', ')
                    }
                    entry {
                        if (authorName) {
                            author {
                                name(authorName)
                            }
                        }
                        title(v.structuredDoctitle.combined)
                        link(href: "$base/$k")
                        updated(updateDate)
                        published(publishDate)
                        summary(v.attributes.description ?: '')
                    }
                }
            }
        }
        feedFile.text = XmlUtil.serialize(blogs)
    }

    static void main(String... args) {
        def sourcesDir = args[0] as File
        def outputDir = args[1] as File
        def sitemapFilename = args[2]
        def generator = new SiteGenerator(sourcesDir: sourcesDir, outputDir: outputDir, sitemapFilename: sitemapFilename)
        boolean watchMode = args.length > 3 ? Boolean.valueOf(args[3]) : false
        generator.generateSite()

        if (watchMode) {
            println "Started watch mode"
            def watcher = FileSystems.default.newWatchService()

            sourcesDir.toPath().register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY)

            sourcesDir.eachDirRecurse { File f ->
                f.toPath().register(watcher,
                        ENTRY_CREATE,
                        ENTRY_DELETE,
                        ENTRY_MODIFY)
            }

            def existingDirectories = ['pages', 'layouts', 'includes', 'html', 'assets', 'css', 'fonts', 'img', 'js', 'vendor']

            while (true) {
                def key = watcher.take()
                def pollEvents = (List<WatchEvent<Path>>) key.pollEvents()

                def changed = pollEvents.collect { "${it.context()}".toString() }.join(', ')

                // only generate when the event refers to the actual file modified / created / added
                // as otherwise the watcher service generates two events:
                // 1) one for directory containing the modified file, and
                // 2) one for the actual file being modified
                // this checks avoid getting two events for one change
                if (existingDirectories.every { !changed.contains(it) }) {
                    try {
                        println "Regenerating site due to changes in: ${changed}"
                        // todo: selective regeneration
                        generator.generateSite()
                    } finally {
                        key.reset()
                    }
                }
            }
        }
    }

    static List<String> decomposeVersion(String version) {
        String qualifier = ''
        if (version.indexOf('-')>0) {
            qualifier = version.substring(version.indexOf('-'))
            version = version - qualifier
        }
        List<String> parts = version.split(/\./).toList()
        if (qualifier) {
            parts << qualifier
        }
        parts
    }

    static boolean exists(String u) {
        def url = new URL(u)
        HttpURLConnection.setFollowRedirects(false)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("HEAD")
        // pretend to be a browser to keep fussy websites a little happier
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)")
        return connection.responseCode == HttpURLConnection.HTTP_OK
    }
}
