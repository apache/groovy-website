import generator.DocUtils
import org.asciidoctor.ast.DocumentHeader

modelTypes = {
    DocumentHeader header
    String title
    String notes
}

title = header.documentTitle.main
def metas = [:]
if (header.attributes.keywords) {
    metas.keywords = header.attributes.keywords
}
if (header.attributes.description) {
    metas.description = header.attributes.description
}

layout 'layouts/main.groovy', true,
        pageTitle: "The Apache Groovy programming language - Blogs - $title",
        extraStyles: ['https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.min.css'],
        extraMeta: metas,
        extraFooter: contents {
            script(src:'https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.min.js') { }
            script { yieldUnescaped "document.addEventListener('DOMContentLoaded',prettyPrint)" }
        },
        mainContent: contents {
            Map options = [attributes:[DOCS_BASEURL:DocUtils.DOCS_BASEURL]]
            def notesAsHTML = asciidocText(notes,options)
            def matcher = notesAsHTML =~ /<h2 id="(.+?)">(.+?)<\/h2>/
            def sections = [:]
            while (matcher.find()) {
                sections[matcher.group(1)] = matcher.group(2)
            }

            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                li(class:'active') {
                                    a(href: '#doc', title)
                                }
                                sections.each { k,v ->
                                    li {
                                        a(href:"#$k", class: 'anchor-link', v)
                                    }
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            a(name:"doc"){}
                            h1(title)
                            if (header.author) {
                                p {
                                    yield 'Author: '
                                    i(header.author.fullName)
                                }
                            } else if (header.authors) {
                                p {
                                    yield 'Authors: '
                                    i(header.authors*.fullName.join(', '))
                                }

                            }
                            if (header.revisionInfo?.date) {
                                p("Published: ${header.revisionInfo.date}")
                            }
                            if (header.attributes.updated) {
                                p("Updated: ${header.attributes.updated}")
                            }
                            hr()
                            yieldUnescaped notesAsHTML
                        }
                    }
                }
            }
        }
