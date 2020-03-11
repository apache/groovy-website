layout 'layouts/main.groovy', true,
        pageTitle: 'The Apache Groovy programming language - Developer Guidelines',
        mainContent: contents {
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                include template: 'includes/support-navbar.groovy'
                                li(class: 'active') {
                                    a(href: 'guidelines.html') {
                                        strong 'Developer Guidelines'
                                    }
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            include template: 'includes/contribute-button.groovy'
                            h1 {
                                i(class: 'fa fa-map-signs') {}
                                yield ' Developer Guidelines'
                            }
                            article {
                                h2 'Useful build tasks'
                                ul {
                                    li "`installGroovy` or `iG` will build a local install of Groovy which can be used for testing (consider `-PskipIndy` if you don't need indy and want a faster build)"
                                    li '`install` will publish to local Maven repo'
                                    li '`testAll` to run the test suite'
                                    li '`groovydocAll` to produce Groovydoc for all modules'
                                    li '`:test --tests=some.package.SomeTest` to run an individual test'
                                }
                                h2 'Build cache'
                                p """
                                    The Groovy build makes use of Gradle's build cache. This speeds up the build in many scenarios
                                    but sometimes gets in the way (we no doubt have tasks in our build that aren't properly configured wrt stale output).
                                    In such cases, use `--no-build-cache` when running tasks.
                                """
                                h2 'Binary compatibility'
                                p """
                                    We regard binary compatibility as very important. It's not simply an issue related to
                                    how our build works but it impacts many Groovy users. When making changes API, it's not just a case of
                                    checking for all usages within the Groovy codebase that might be impacted but also whether
                                    changes will impact other projects and users of Groovy.
                                    In particular changes related to the Groovy runtime must be done with extreme care.
                                    These are the classes that compiled Groovy code might call into during execution.
                                """
                                p """
                                    In general, we try to evolve the Groovy codebase to minimise impact on Groovy-based frameworks.
                                    For example, we'd ideally like a Grails application built with Groovy 3 to work with a Grails plugin compiled under Groovy 2.5.
                                    This isn't always possible but we should then make life easier for frameworks by appropriate documentation when a breaking change is needed (see hints below).
                                """
                                p """
                                    In theory, classes under the `org.codehaus` and 'org.apache' packages are more internal than those in the `groovy` package.
                                    But users of Groovy will use any public/protected class, so make changes in all areas of the codebase with care.
                                    [NOTE: If we start using JDK9+ modules more we might be able to alter this approach but we haven't started down that path yet.]  
                                """
                                p {
                                    yield 'Hints:'
                                    ul {
                                        li 'Run the `:binary-compatibility:checkBinaryCompatibility` task before committing.'
                                        li 'If a public/protected method needs a new parameter, keep the existing method and create an additional one with the extra parameter. Sometimes the old method should be `@Deprecated`.'
                                        li 'If you want to rename a method, keep the old one and (if appropriate) deprecate the old one rather than just rename the existing one.'
                                        li 'If the return type of a method changes but nothing else, consider creating a bridge method - the build is set up to use a `Bridger` utility to do this for `$$bridge` methods.'
                                        li "If the change can't be totally hidden, start a discussion in the dev/users mailing list as appropriate to investigate usages of the API by others."
                                        li "If the change can't be totally hidden, do mark the related JIRA issue (create one if needed) with the `breaking` label."
                                        li "If the change can't be totally hidden, do add a little summary/migration note in the relevant release note(s)."
                                    }
                                }
                                h2 'Checkstyle'
                                p 'TBD'
                                h2 'Codenarc'
                                p 'TBD'
                                h2 'Coverage'
                                p 'TBD'
                            }
                        }
                    }
                }
            }
        }
