div(class: 'navbar navbar-default navbar-static-top', role: 'navigation') {
    div(class: 'container') {
        div(class: 'navbar-header') {
            button(type: 'button', class: 'navbar-toggle', 'data-toggle': 'collapse', 'data-target': '.navbar-collapse') {
                span(class: 'sr-only') {}
                span(class: 'icon-bar') {}
                span(class: 'icon-bar') {}
                span(class: 'icon-bar') {}
            }
            a(class: 'navbar-brand', href: relative('index.html')) {
                i(class: 'fa-classic fa-solid fa-star') {}
                yield ' Apache Groovy'
                yieldUnescaped '&reg;'
            }
        }
        div(class: 'navbar-collapse collapse') {
            ul(class: 'nav navbar-nav navbar-right') {
                menu['Groovy'].each { menuItem ->
                    li(class: category == menuItem.name ? 'active' : '') { a(href: relative(menuItem.link), menuItem.name) }
                }
                li {
                    a('data-effect': 'st-effect-9', class: 'st-trigger', href: '#', 'Socialize')
                }
                li(class: (category == 'Search') ? 'active' : '') {
                    a(href: relative('search.html')) {
                        i(class: 'fa-classic fa-solid fa-magnifying-glass') {}
                    }
                }
                li {
                    button(id: 'theme-switcher', class: 'theme-switcher', type: 'button', title: 'Toggle theme', 'aria-label': 'Toggle theme') {
                        span(class: 'theme-icon', '')
                    }
                }
            }
        }
    }
}
