layout 'layouts/main.groovy', true,
        pageTitle: 'The Apache Groovy programming language - FAQ - Frequently Asked Questions',
        mainContent: contents {
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                li {
                                    a(href: 'documentation.html', 'Documentation')
                                }
                                docSections.each { section ->
                                    li { a(href: "documentation.html#${section.anchor}", class: 'anchor-link', section.name) }
                                }
                                li {
                                    a(href: "documentation.html#all-versions", class: 'anchor-link', 'Documentation for other versions')
                                }
                                li(class: 'active') {
                                    a(href: 'faq.html') { strong('FAQ') }
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            include template: 'includes/contribute-button.groovy'
                            h1 {
                                i(class: 'fa-classic fa-regular fa-question-circle') {}
                                yieldUnescaped ' Frequently Asked Questions'
                            }
                            hr(class: 'divider')
                            article {
                                h2 'Is Groovy an Open Source project?'
                                p {
                                    yield 'Yes, Groovy is an Open Source programming language project, licensed under the '
                                    a(href: 'http://www.apache.org/licenses/LICENSE-2.0', 'Apache License v2')
                                    yield '. You can see the license header in all the source files of the project, as well as a '
                                    a(href: 'https://github.com/apache/groovy/blob/master/LICENSE', 'license file')
                                    yield ' at the root of the project'
                                }

                                h2 'What are the differences between Groovy and Java?'
                                p {
                                    yield 'Groovy is closely related to Java but offers many productivity features like closures, builders, and dynamic typing. Learn more about the '
                                    a(href: 'differences.html', 'differences between Groovy and Java')
                                    yield '.'
                                }

                                h2 'Does Groovy support static typing?'
                                p {
                                    yield 'Yes! While Groovy is dynamic by nature, it provides `@TypeChecked` for compile-time checking and `@CompileStatic` for Java-like performance. See the '
                                    a(href: 'releasenotes/groovy-2.0.html', 'Groovy 2.0 release notes')
                                    yield ' for details.'
                                }

                                h2 'How do I install Groovy?'
                                p {
                                    yield 'The easiest way to install Groovy is via '
                                    a(href: 'http://sdkman.io', 'SDKMAN!')
                                    yield '. Alternatively, you can download the binary distribution from our '
                                    a(href: 'download.html', 'download page')
                                    yield '.'
                                }

                                h2 'Where can I get help or ask questions?'
                                p {
                                    yield 'You can join the '
                                    a(href: 'mailing-lists.html', 'mailing lists')
                                    yield ', chat with the community on '
                                    a(href: 'https://www.groovycommunity.com/', 'Slack')
                                    yield ', or ask questions on '
                                    a(href: 'https://stackoverflow.com/questions/tagged/groovy', 'Stack Overflow')
                                    yield '.'
                                }
                            }
                        }
                    }
                }
            }
        }
