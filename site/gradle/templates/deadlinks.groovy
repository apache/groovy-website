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
html {
    head {
        title 'Dead links report'
    }
    body {
        h1('Dead links report')
        if (!deadLinks) {
            p("No dead link found. All green!")
        } else {
            p "Found a total of ${deadLinks.values().sum{ it.size() }} dead links in the following files:"
            deadLinks.each { entry ->
                def (page, links) = [entry.key, entry.value]
                h3 { a(href:page,page) }
                ul {
                    links.each { link ->
                        li("At line ${link.line}, dead link to ${link.link}")
                    }
                }
            }
            p "Note: not all dead links can be fixed directly in the Groovy website project - some are generated from the Groovy documentation and must be fixed there."
        }
    }
}
