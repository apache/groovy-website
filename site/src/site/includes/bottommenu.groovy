
// footer
footer(id: 'footer') {
    div(class: 'row') {
        div(class: 'colset-3-footer') {
            menu.entrySet().eachWithIndex { entry, i ->
                def (name, menu) = [entry.key, entry.value]
                div(class: "col-${i+1}") {
                    h1(name)
                    ul {
                        menu.each { menuItem ->
                            li { a(href: relative(menuItem.link), menuItem.name) }
                        }
                    }
                }
            }
            div(class: 'col-right') {
                p {
                    yield 'The Groovy programming language is supported by the '
                    a href: 'http://www.apache.org', 'Apache Software Foundation'
                    yield " and the Groovy community."
                }
                div('text-align':'right') {
                    img(src: relative('img/asf_logo.png'), title: 'The Apache Software Foundation', alt: 'The Apache Software Foundation', style:'width:60%')
                }
                p "Apache&reg; and the Apache feather logo are either registered trademarks or trademarks of The Apache Software Foundation."
            }
        }
        div(class: 'clearfix', "&copy; 2003-${Calendar.instance[Calendar.YEAR]} the Apache Groovy project &mdash; " +
                "Groovy is Open Source: ${$a(href: 'http://www.apache.org/licenses/LICENSE-2.0.html', alt: 'Apache 2 License', 'license')}, ${$a(href: 'https://privacy.apache.org/policies/privacy-policy-public.html', 'privacy policy')}.")
    }
}
