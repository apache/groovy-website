layout 'layouts/main.groovy', true,
        pageTitle: "The Apache Groovy programming language - Blogs",
        mainContent: contents {
            def sorted = list.sort()
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3') {
                            ul(class: 'nav-sidebar') {
                                li(class:'active') {
                                    a(href: '#gep', "Blogs")
                                }
                                sorted.each { blog ->
                                    li { a(href: "#$blog.key", class: 'anchor-link', "$blog.key") }
                                }
                            }
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            h1('Blogs for Groovy')
                            p 'Here you can find the Blogs for the Groovy programming language:'
                            ul {
                                sorted.each { blog ->
                                    li {
                                        a(href: "${blog.key}.html", "$blog.key: $blog.value")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
