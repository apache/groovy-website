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

import com.yahoo.platform.yui.compressor.CssCompressor
import org.apache.tools.ant.filters.BaseFilterReader

class CssFilter extends BaseFilterReader {
    Writer writer
    Thread worker

    CssFilter(Reader reader) {
        super(new PipedReader())
        writer = new PipedWriter(this.@in)
        def compressor = new CssCompressor(reader)
        reader.close()
        worker = Thread.start {
            compressor.compress(writer, -1)
            writer.close()
        }
    }

    void close() {
        worker.join()
        super.close()
    }

}
