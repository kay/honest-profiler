/**
 * Copyright (c) 2015 Richard Warburton (richard.warburton@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 **/
package com.insightfullogic.honest_profiler.ports;

import com.insightfullogic.honest_profiler.core.collector.FlamegraphCollector;
import com.insightfullogic.honest_profiler.core.collector.FlamegraphData;
import com.insightfullogic.honest_profiler.core.sources.LogSource;
import com.insightfullogic.honest_profiler.ports.sources.FileLogSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Flamegraph {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.print(
                "Usage: java com.insightfullogic.honest_profiler.ports.Flamegraph <profile.hpl> <profile.txt>\n" +
                "\n" +
                "The output needs to be processed with the tools at https://github.com/brendangregg/FlameGraph to produce the actual flamegraph\n");
            System.exit(1);
        }

        String in = args[0], out = args[1];
        LogSource source = new FileLogSource(new File(in));

        try (Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)))) {
            FlamegraphData  data = FlamegraphCollector.readFlamegraph(source);

            data.writeTo(output);
        }
    }
}
