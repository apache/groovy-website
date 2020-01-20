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
package util

import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.gradle.api.logging.Logger
import org.apache.tools.ant.util.FileUtils

class CheckLinks {
    Logger logger = null
    List<String> excludeFromChecks = []
    final Map<File, List> deadLinks = [:]
    Map<Object, Object> isDead = [:]
    def baseDir

    boolean checkIsDead(link, currentPath) {
        if (excludeFromChecks.any { link.startsWith(it) }) {
            // skip checking those links because they dramatically increase build time
            // while being most likely ok because generated through changelog parsing
            return false
        }

        try {
            URL url
            boolean rejected = false
            try {
                url = URI.create(link).toURL()
            } catch (e) {
                if (e.message.contains('URI is not absolute')) {
                    rejected = true
                }
            }
            if (rejected || !url) {
                def path = "file:///${new File("$baseDir/${currentPath ? currentPath + '/' : ''}$link").canonicalPath.replace('\\', '/')}"
                url = URI.create(path).toURL()
            }
            logger?.debug("Checking URL: $url")
            def cx = url.openConnection()
            if (cx instanceof HttpURLConnection) {
                CloseableHttpClient httpclient = HttpClients.createDefault()
                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(5_000)
                        .setConnectTimeout(5_000)
                        .setConnectionRequestTimeout(5_000)
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build()
                HttpGet httpget = new HttpGet(link)
                httpget.config = requestConfig
                CloseableHttpResponse response
                try {
                    response = httpclient.execute(httpget)
                    if (response.statusLine.statusCode == 404) {
                        return true
                    }
                } finally {
                    response.close()
                }
            }
        } catch (e) {
            logger?.debug e.message
            return true
        }
        return false
    }

    def checkLink(List dead, int line, String link, currentPath) {
        if (!isDead.containsKey(link)) isDead[link] = checkIsDead(link, currentPath)
        if (isDead[link]) {
            dead << [line:line, link:link]
        }
    }

    def checkPage(File f) {
        def currentPath = FileUtils.getRelativePath(baseDir, f.parentFile)
        f.eachLine('utf-8') { String line, int nb ->
            def dead = []
            [/\shref=['"](.+?)['"]/, /src=['"](.+?)['"]/].each { regex ->
                def matcher = line =~ regex
                if (matcher) {
                    matcher.each {
                        def linkpath = it[1]
                        checkLink(dead, nb, linkpath, currentPath)
                    }
                }
            }
            if (dead) {
                deadLinks[f] = dead
            }
        }
    }

}
