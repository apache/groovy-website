import generator.DocUtils

def keywords = [:].withDefault{ 0 }
list.removeAll { k, v ->
    v.attributes.draft
}
list.each { k, v ->
    v.attributes.keywords?.split(',')*.trim().each{ keywords[it]++ }
}
layout 'layouts/main.groovy', true,
        pageTitle: "The Apache Groovy programming language - Blogs",
        extraFooter: contents {
            script(src: 'https://cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js') { }
            script {
                yieldUnescaped '''
                var blogList = new List('blog-list', {
                    valueNames: ['name'],
                    page: 10,
                    pagination: true
                });
                var blogIndex = new List('blog-index', {
                    page: 30,
                    pagination: true
                });
                '''
            }
            script(src: 'https://cdn.amcharts.com/lib/5/index.js') { }
            script(src: 'https://cdn.amcharts.com/lib/5/wc.js') { }
            script {
                yieldUnescaped """
                var root = am5.Root.new("chartdiv");
                var wc = root.container.children.push(am5wc.WordCloud.new(root, {
                    colors: am5.ColorSet.new(root, {})
                }));
                wc.data.setAll([
                ${keywords.collect{ keyword, count ->
                    /{ category: "$keyword", value: $count }/
                }.join(', ')}
                ]);
                wc.labels.template.setAll({
                    paddingTop: 5,
                    paddingBottom: 5,
                    paddingLeft: 5,
                    paddingRight: 5,
                    fontFamily: "Courier New"
                });
                """
            }
        },
        mainContent: contents {
            def sorted = list.sort { e -> e.value.attributes.updated ?: e.value.revisionInfo.date }
            div(id: 'content', class: 'page-1') {
                div(class: 'row') {
                    div(class: 'row-fluid') {
                        div(class: 'col-lg-3', id: 'blog-index') {
                            ul(class: 'nav-sidebar list') {
                                li(class:'active') {
                                    a(href: '/blog/', "Blogs")
                                }
                                sorted.reverseEach { blog ->
                                    li { a(href: blog.key, blog.value.structuredDoctitle.combined) }
                                }
                            }
                            ul(class: 'pagination')
                        }

                        div(class: 'col-lg-8 col-lg-pull-0') {
                            h1('Blogs for Groovy')
                            div(id: 'chartdiv') { }
                            p 'Here you can find the Blogs for the Groovy programming language:'
                            div(id: 'blog-list') {
                                div {
                                    span('Search: ')
                                    input(type: 'text', class: 'search')
                                }
                                ul(class: 'list') {
                                    sorted.reverseEach { k, v ->
                                        li {
                                            p(class: 'name') {
                                                a(href: k, v.structuredDoctitle.combined)
                                                br()
                                                def author = v.authors.join(', ')
                                                yield "Published by $author on ${DocUtils.prettyDate(v.revisionInfo.date)}"
                                                if (v.attributes.updated) {
                                                    br()
                                                    yield "Last updated: ${DocUtils.prettyDate(v.attributes.updated)}"
                                                }
                                                if (v.attributes.description) {
                                                    br()
                                                    yield v.attributes.description
                                                }
                                            }
                                        }
                                    }
                                }
                                ul(class: 'pagination')
                            }
                        }
                    }
                }
            }
        }
