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
package generator

import groovy.text.markup.BaseTemplate
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import groovy.transform.CompileStatic

@CompileStatic
abstract class PageTemplate extends BaseTemplate {
    public static final String BASEDIR = "basePath"

    private final Map model

    PageTemplate(
            final MarkupTemplateEngine templateEngine,
            final Map model, final Map<String, String> modelTypes, final TemplateConfiguration configuration) {
        super(templateEngine, model, modelTypes, configuration)
        this.model = model
    }

    String relative(String path) {
        String base = (String) model.get(BASEDIR)
        if (base && !path.startsWith('http') && !path.startsWith(File.separator)) {
            String up = "../"
            "${up*(1+base.count(File.separator))}$path"
        } else {
            path
        }
    }

    /**
     * Converts and outputs asciidoctor markup into HTML
     * @param body the asciidoctor markup
     */
    void asciidoc(String body, Map options=[:]) {
        yieldUnescaped asciidocText(body, options)
    }

    /**
     * Converts and returns asciidoctor markup into HTML. This method
     * does *not* automatically render the result so it is possible
     * to post-process the generated HTML.
     * @param body the asciidoctor markup
     */
    String asciidocText(String body, Map options=[:]) {
        def asciidoctor = AsciidoctorFactory.instance
        def attributes = options.attributes
        if (!attributes) {
            attributes = [:]
            options.put('attributes', attributes)
        }
        attributes['source-highlighter'] = 'prettify'
        asciidoctor.convert(body,options)
    }

    String latestDocURL(String target) {
        "${DocUtils.DOCS_BASEURL}/html/$target"
    }
}
