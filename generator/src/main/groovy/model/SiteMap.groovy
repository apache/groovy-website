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
package model

import groovy.transform.ToString
import org.codehaus.groovy.control.CompilerConfiguration

import groovy.transform.CompileStatic
import org.codehaus.groovy.control.customizers.ImportCustomizer

@CompileStatic
@ToString(includeNames=true)
class SiteMap {
    final List<Section> documentationSections = []
    final List<Distribution> distributions = []
    final Menu menu = new Menu()
    final Ecosystem ecosystem = new Ecosystem()
    final Events allEvents = new Events()
    final Library library = new Library()
    final List<String> allDocVersions = []
    final List<Page> pages = []
    final List<UserGroup> usergroups = []
    final List<Video> videos = []
    final List<Course> courses = []
    boolean changelogs = true
    boolean releaseNotes = true
    boolean wiki = true
    boolean blog = true

    private SiteMap() {}

    static SiteMap from(File source) {
        CompilerConfiguration config = new CompilerConfiguration()
        def customizer = new ImportCustomizer()
        config.addCompilationCustomizers(customizer)
        customizer.addStaticImport('generator.DocUtils','DOCS_BASEURL')
        config.scriptBaseClass = 'groovy.util.DelegatingScript'
        GroovyShell shell = new GroovyShell(config)
        def script = shell.parse(source)

        def result = new SiteMap()
        ((DelegatingScript)script).setDelegate(result)
        script.run()

        result
    }

    private void documentation(Closure docSpec) {
        def clone = docSpec.rehydrate(this, this, this)
        clone()
    }

    private void section(String name, String icon, Closure sectionSpec) {
        Section section = new Section(name:name, icon:icon)
        def spec = sectionSpec.rehydrate(section,section,section)
        spec()
        documentationSections.add(section)
    }

    private void groovyDocumentationVersions(List<String> allDocVersions) {
        this.allDocVersions.addAll(allDocVersions)
    }

    private void downloads(Closure dlSpec) {
        def clone = dlSpec.rehydrate(this, this, this)
        clone()
    }

    private void pages(Closure pagesSpec) {
        def clone = pagesSpec.rehydrate(this, this, this)
        clone()
    }

    private void usergroups(Closure groupsSpec) {
        def clone = groupsSpec.rehydrate(this ,this ,this)
        clone()
    }

    private void videos(Closure videosSpec) {
        def clone = videosSpec.rehydrate(this, this, this)
        clone()
    }

    private void courses(Closure coursesSpec) {
        def clone = coursesSpec.rehydrate(this, this, this)
        clone()
    }

    private void distribution(String name, Closure distSpec) {
        Distribution dist = new Distribution(name: name)
        def clone = distSpec.rehydrate(dist, dist, dist)
        clone()
        distributions.add(dist)
    }

    private void menu(Closure menuSpec) {
        def clone = menuSpec.rehydrate(menu, menu, menu)
        clone()
    }

    private void ecosystem(Closure ecoSpec) {
        def clone = ecoSpec.rehydrate(ecosystem, ecosystem, ecosystem)
        clone()
    }

    private void allEvents(Closure eventsSpec) {
        def clone = eventsSpec.rehydrate(allEvents, allEvents, allEvents)
        clone()
    }

    private void books(Closure booksSpec) {
        def clone = booksSpec.rehydrate(library, library, library)
        clone()
    }

    private void page(String source, String target, Map model = [:]) {
        pages.add(new Page(source:source, target: target, model: model))
    }

    private void userGroup(String name, Closure groupSpec) {
        def group = new UserGroup(name: name)
        def clone = groupSpec.rehydrate(group,group,group)
        clone()
        usergroups.add(group)
    }

    private void video(String title, Closure videoSpec) {
        def video = new Video(title: title)
        def clone = videoSpec.rehydrate(video, video, video)
        clone()
        videos.add(video)
    }

    private void course(String title, Closure courseSpec) {
        def course = new Course(title: title)
        def clone = courseSpec.rehydrate(course,course,course)
        clone()
        courses.add(course)
    }
}
