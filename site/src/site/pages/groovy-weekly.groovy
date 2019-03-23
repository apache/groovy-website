layout 'layouts/main.groovy', true,
        pageTitle: 'The Apache Groovy programming language - Groovy newsletter',
        mainContent: contents {
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            include template: 'includes/support-navbar.groovy'
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            include template: 'includes/contribute-button.groovy'
                            h1 {
                                i(class: 'fa fa-envelope-o') {}
                                yield ' Groovy Newsletter '
                            }
                            article {
                                p 'The Groovy newsletter is no longer produced on a regular basis but you can still view the archives:'
                                ul {
                                    li {
                                        a(href: 'https://us4.campaign-archive2.com/home/?u=cb8b56e9d6a1cb1696cecc673&id=1a76961630',
                                                'View past editions of the Groovy newsletter.')
                                    }
                                }
                                p {
                                    yield 'Be sure to check out the other available places for '
                                    a(href: 'support.html', 'Groovy support')
                                    yield '.'
                                }
                            }
                        }
                    }
                }
            }
        }
