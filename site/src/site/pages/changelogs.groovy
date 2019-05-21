layout 'layouts/main.groovy', true,
        pageTitle: "The Apache Groovy programming language - Changelogs",
        mainContent: contents {
            def majorize = { it.split(/\.|-/)[0..1].join('.')}

            def major = versions.collect([] as Set) { majorize(it) }

            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                li(class:'active') {
                                    a(href: '#changelog', "Changelogs")
                                }
                                major.each { v ->
                                    li { a(href: "#changelog$v", class: 'anchor-link', "Groovy $v") }
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            h1('Changelogs for Groovy')
                            p 'Here you can find the changelogs for the past Groovy releases.'
                            versions.groupBy { majorize(it) }.each {
                                String mj = it.key
                                List<String> minor = it.value
                                a(name: "changelog$mj") {}
                                h2("Groovy $mj")
                                ul {
                                    minor.sort{ a, b ->
                                            aInfo = generator.ChangelogParser.INFO[a]
                                            bInfo = generator.ChangelogParser.INFO[b]
                                            (bInfo ? bInfo.split(' ')[-1] : '~') <=> (aInfo ? aInfo.split(' ')[-1] : '~')
                                    }.each { v ->
                                        def unreleased = v.endsWith('-unreleased')
                                        def ver = unreleased ? v - '-unreleased' : v
                                        li {
                                            yieldUnescaped ( v.endsWith('-unreleased') || (!v.contains('-') && versions.any{ it.startsWith("$v-") }) ? "Aggregate c" : "C" ) + "hangelog for "
                                            a(href: "changelogs/changelog-${v}.html", "Groovy $ver")
                                            yieldUnescaped "&nbsp;&nbsp;[${generator.ChangelogParser.INFO[ver]}]"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
