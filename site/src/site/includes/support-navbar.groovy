
ul(class: 'nav-sidebar') {
    [
            'support': 'Support',
            'https://groovy.apache.org/index': 'Contribute',
            'reporting-issues': 'Reporting issues',
            'mailing-lists': 'Mailing-lists',
            'events': 'Events',
            'usergroups': 'User groups',
            'thanks': 'Thanks',
            'groovy-weekly': 'Groovy newsletter'
    ].each { page, label ->
        if (currentPage == page) {
            li(class: 'active') { a(href: relative("${page}.html")) { strong(label) } }
        } else {
            li { a(href: "${page}.html", label) }
        }
    }
}
br()
include unescaped: 'html/twittersearch.html'
