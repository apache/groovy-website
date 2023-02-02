---
layout: post
title: Deep Learning and Eclipse Collections
date: '2022-10-11T10:41:58+00:00'
permalink: deep-learning-and-eclipse-collections
---
<h2>DeepLearning4J and Eclipse Collections revisited</h2>

<p>In previous blogs, we have covered <a href="https://blogs.apache.org/groovy/entry/deck-of-cards-with-groovy" target="_blank">Eclipse Collections</a> and <a href="https://blogs.apache.org/groovy/entry/detecting-objects-with-groovy-the" target="_blank">Deep Learning</a>. Recently, a couple of the highly recommended katas for Eclipse Collections have been revamped to include "pet" and "fruit" emojis for a little bit of extra fun. What could be better than <i>Learning</i> Eclipse Collections? <i>Deep Learning</i> and Eclipse Collections of course!</p><p>First, we create a <code>PetType</code> enum with the emoji <code>toString</code>, and then <code>Pet</code> and <code>Person</code> records. We'll populate a <code>people</code> list as is done in the kata. The full details are in the <a href="https://github.com/paulk-asert/deep-learning-eclipse-collections" target="_blank">repo</a>.</p><p>Let's use a GQuery expression to explore the pre-populated list:</p><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;">println <span style="color:#9876aa;font-style:italic;">GQ </span>{<br>    <span style="color:#cc7832;">from </span>p <span style="color:#cc7832;">in </span>people<br>    <span style="color:#cc7832;">select </span>p.<span style="color:#9876aa;">fullName</span>, p.<span style="color:#9876aa;">pets<br></span>}<br></pre><p>The result is:</p><p><img src="https://blogs.apache.org/groovy/mediaresource/fb059e16-ae50-4681-8d2f-848b8f98041a" style="width:35%" alt="deep-learning-eclipse-collections pre-populated field"></p><p>Now let's duplicate the assertion from the <code>getCountsByPetType</code> test in exercise3 which checks pet counts:</p><p><img src="https://blogs.apache.org/groovy/mediaresource/1bffcd1c-67e6-41a9-9511-680fc246778e" style="width:90%" alt="2022-10-11 20_06_12-Groovy web console.png"></p><p>As we expect, it passes.</p><p>Now, for a bit of fun, we will use a neural network trained to detect cat and dog images and apply it to our emojis. We'll follow the process described <a href="http://ramok.tech/2018/01/03/java-image-cat-vs-dog-recognizer-with-deep-neural-networks/" target="_blank">here</a>. It uses DeepLearning4J to train and then use a model. The images used to train the model were real cat and dog images, not emojis, so we aren't expecting our model to be super accurate.</p><p>The first attempt was to write the emojis into swing JLabel components and then save using a buffered image. This lead to poor looking images:</p><p><img src="https://blogs.apache.org/groovy/mediaresource/80954fdc-8484-47c5-9cba-c64747e1ea5b" style="width:30%" alt="PetAsFonts.jpg"></p><p>And consequently, poor image inference. Recent JDK versions on some platforms might do better but we gave up on this approach.</p><p>Instead, emoji image files from the <a href="https://fonts.google.com/noto/specimen/Noto+Color+Emoji?preview.text=%F0%9F%98%BB%F0%9F%90%B6%F0%9F%90%B9%F0%9F%90%A2%F0%9F%90%A6%F0%9F%90%8D&amp;preview.text_type=custom" target="_blank">Noto Color Emoji</a> font were used and saved under the pet type in the resources folder. These look much nicer:</p><p><img src="https://blogs.apache.org/groovy/mediaresource/3513ee3e-98cf-4439-9496-20ae9d976262" style="width:50%" alt="2022-10-11 18_24_38-Noto Color Emoji - Google Fonts.png"></p><p>Here is the code which makes use of those saved images to detect the animal types (note the use of type aliasing since we have two <code>PetType</code> classes; we rename one to <code>PT</code>):</p><pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'JetBrains Mono',monospace;font-size:9.6pt;"><span style="color:#cc7832;">import </span>ramo.klevis.ml.vg16.PetType <span style="color:#cc7832;">as </span>PT<br><span style="color:#cc7832;">import </span>ramo.klevis.ml.vg16.VG16ForCat<br><br><span style="color:#cc7832;">var </span>vg16ForCat = <span style="color:#cc7832;">new </span>VG16ForCat().tap<span style="font-weight:bold;">{ </span>loadModel() <span style="font-weight:bold;">}<br></span><span style="color:#cc7832;">var </span>results = []<br>people.each<span style="font-weight:bold;">{ </span>p <span style="font-weight:bold;">-&gt;<br></span><span style="font-weight:bold;">    </span>results &lt;&lt; p.<span style="color:#9876aa;">pets</span>.collect <span style="font-weight:bold;">{ </span>pet <span style="font-weight:bold;">-&gt;<br></span><span style="font-weight:bold;">        </span><span style="color:#cc7832;">var </span>file = <span style="color:#cc7832;">new </span>File(<span style="color:#6a8759;">"resources/</span>$<span style="font-weight:bold;">{</span>pet.<span style="color:#9876aa;">type</span>.name()<span style="font-weight:bold;">}</span><span style="color:#6a8759;">.png"</span>)<br>        PT petType = vg16ForCat.detectCat(file, <span style="color:#6897bb;">0.675d</span>)<br>        <span style="color:#cc7832;">var </span>desc = <span style="color:#cc7832;">switch</span>(petType) {<br>            <span style="color:#cc7832;">case </span>PT.<span style="color:#9876aa;font-style:italic;">CAT </span>-&gt; <span style="color:#6a8759;">'is a cat'<br></span><span style="color:#6a8759;">            </span><span style="color:#cc7832;">case </span>PT.<span style="color:#9876aa;font-style:italic;">DOG </span>-&gt; <span style="color:#6a8759;">'is a dog'<br></span><span style="color:#6a8759;">            </span><span style="color:#cc7832;">default </span>-&gt; <span style="color:#6a8759;">'is unknown'<br></span><span style="color:#6a8759;">        </span>}<br>        <span style="color:#6a8759;">"</span>$pet.<span style="color:#9876aa;">name </span>$desc<span style="color:#6a8759;">"<br></span><span style="color:#6a8759;">    </span><span style="font-weight:bold;">}<br></span><span style="font-weight:bold;">}<br></span>println results.flatten().join(<span style="color:#6a8759;">'</span><span style="color:#cc7832;">\n</span><span style="color:#6a8759;">'</span>)<br></pre><p>Note that the model exceeds the maximum allowable size for normal github repos, so you should create it following the original repo <a href="https://github.com/klevis/CatAndDogRecognizer" target="_blank">instructions</a> and then store the resulting model.zip in the resources folder.</p><p>When we run the script, we get the following output:</p>
<pre><span style="color:#dd4444">
[main] INFO org.nd4j.linalg.factory.Nd4jBackend - Loaded [CpuBackend] backend
...
[main] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Blas vendor: [OPENBLAS]
...
============================================================================================================================================
VertexName (VertexType)                 nIn,nOut       TotalParams    ParamsShape                    Vertex Inputs
============================================================================================================================================
input_1 (InputVertex)                   -,-            -              -                              -
block1_conv1 (Frozen ConvolutionLayer)  3,64           1792           b:{1,64}, W:{64,3,3,3}         [input_1]
block1_conv2 (Frozen ConvolutionLayer)  64,64          36928          b:{1,64}, W:{64,64,3,3}        [block1_conv1]
block1_pool (Frozen SubsamplingLayer)   -,-            0              -                              [block1_conv2]
block2_conv1 (Frozen ConvolutionLayer)  64,128         73856          b:{1,128}, W:{128,64,3,3}      [block1_pool]
block2_conv2 (Frozen ConvolutionLayer)  128,128        147584         b:{1,128}, W:{128,128,3,3}     [block2_conv1]
block2_pool (Frozen SubsamplingLayer)   -,-            0              -                              [block2_conv2]
block3_conv1 (Frozen ConvolutionLayer)  128,256        295168         b:{1,256}, W:{256,128,3,3}     [block2_pool]
block3_conv2 (Frozen ConvolutionLayer)  256,256        590080         b:{1,256}, W:{256,256,3,3}     [block3_conv1]
block3_conv3 (Frozen ConvolutionLayer)  256,256        590080         b:{1,256}, W:{256,256,3,3}     [block3_conv2]
block3_pool (Frozen SubsamplingLayer)   -,-            0              -                              [block3_conv3]
block4_conv1 (Frozen ConvolutionLayer)  256,512        1180160        b:{1,512}, W:{512,256,3,3}     [block3_pool]
block4_conv2 (Frozen ConvolutionLayer)  512,512        2359808        b:{1,512}, W:{512,512,3,3}     [block4_conv1]
block4_conv3 (Frozen ConvolutionLayer)  512,512        2359808        b:{1,512}, W:{512,512,3,3}     [block4_conv2]
block4_pool (Frozen SubsamplingLayer)   -,-            0              -                              [block4_conv3]
block5_conv1 (Frozen ConvolutionLayer)  512,512        2359808        b:{1,512}, W:{512,512,3,3}     [block4_pool]
block5_conv2 (Frozen ConvolutionLayer)  512,512        2359808        b:{1,512}, W:{512,512,3,3}     [block5_conv1]
block5_conv3 (Frozen ConvolutionLayer)  512,512        2359808        b:{1,512}, W:{512,512,3,3}     [block5_conv2]
block5_pool (Frozen SubsamplingLayer)   -,-            0              -                              [block5_conv3]
flatten (PreprocessorVertex)            -,-            -              -                              [block5_pool]
fc1 (Frozen DenseLayer)                 25088,4096     102764544      b:{1,4096}, W:{25088,4096}     [flatten]
fc2 (Frozen DenseLayer)                 4096,4096      16781312       b:{1,4096}, W:{4096,4096}      [fc1]
predictions (OutputLayer)               4096,2         8194           b:{1,2}, W:{4096,2}            [fc2]
--------------------------------------------------------------------------------------------------------------------------------------------
            Total Parameters:  134268738
        Trainable Parameters:  8194
           Frozen Parameters:  134260544
============================================================================================================================================
...</span>
Tabby is a cat
Dolly is a cat
Spot is a dog
Spike is a dog
Serpy is a cat
Tweety is unknown
Speedy is a dog
Fuzzy is unknown
Wuzzy is unknown
</pre>
<p>As we can see, it correctly predicted the cats (Tabby and Dolly) and dogs (Spot and Spike) but incorrectly thought a snake (Serpy) was a cat and a turtle (Speedy) was a dog. Given the lack of detail in the emoji images compared to the training images, this lack of accuracy isn't unexpected. We could certainly use better images or train our model differently if we wanted better results but it is fun to see our model not doing too badly even with emojis!</p>
