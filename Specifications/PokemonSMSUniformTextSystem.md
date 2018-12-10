# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Uniform Text System Specification("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
Using the license specified by the project, you may, with only the restrictions detailed below,
(a)Use this document to produce a complete or partial implementation of PokemonSMS, 
(b)Use this document as reference material to create other related projects or derivative works,
(c)Transmit and/or Distribute Verbatim Copies of this document,
(d)Transmit and/or Distribute Partial copies of this document, which link to this document and include this copyright notice,
(e)Use this document as a guideline to design other specifications for projects, related or not,
(f)Quote parts of this document for works of any kind, that link to this document,
(g)Use part or the whole of this document for any purpose which does not constitute copyright infringement under the intellectual property laws of your jurisdiction.
<br/><br/>
The following restrictions (and only the following restrictions) apply to the above:
You may not, under any circumstances, 
(a)Claim that this document, or any part of it, belongs to you, 
(b)Use this document in any way that would be unlawful in your jurisdiction, or in Canada, 
(c)Use this document in a way that would infringe upon the copyright of any person, organization, or corporation, without prior written consent from that entity or an entity which has been designated by that company as a legal authority on their behalf, including the Owner, or Sentry Game Engine.
<br/><br/>
  This Document, and this project are distributed with the intention that they will be useful and complete. However this document and this project are provided on an AS-IS basis, without any warranties of Any Kind, including the implied warranties of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. As such, any person using this document for any reason does so at their own risk.  By using this document, you explicitly agree to release The Owner, and any person who might have distributed a copy of this document to you from all liability connected with your use of this document
<br/><br/>

## Information ##
This document defines the uniform text system used by PokemonSMS, to define how various text elements encountered should be printed/drawn. 

# Text System #
PokemonSMS's Text System is broken into 2 parts: Text Components, which are the actual components of text that can be printed or drawn; and Translation Blocks, which are how Unlocalized String are converted to concrete Text Components, and defines locale information. 

## Text Components ##
Text Components are json objects which describe text. Text Components have 2 direct operations: format, and display. 
A Text Component contains a type, usually some text, other data specified by the type, and an optional continue field which contains subsequent text components. 
How format and display work depend on the type of the component. 

The following types are provided by this specification: 
<ul>
<li>raw</li>
<li>formatted</li>
<li>translatable</li>
<li>argument (does not have a "text" field)</li>
<li>image</li>
</ul>

A format operation acts like the following: 
Start with the top level component, a set of arguments which are text components, and the current Translation Table, and the index of the default target being 0. 
If the component being formatted is an "image", "raw", or "formatted", pass.<br/>
Otherwise, if the component being formatted is an "argument" perform the following steps:
<ol type="1">
<li>Select the target argument, given by the "target" field of the component, or the default target if the "target" field does not exist.</li>
<li>If the default target was selected, increment the index of the default target</li>
<li>Replace this with a copy of the argument, and format it</li>
</ol>
Otherwise, if the component being formatted is "translatable", perform the following steps:
<ol type="1">
<li>Lookup the component in the current translation table with the key given by the text field</li>
<li>If such a component exists, then replace this with a copy of that component, and format it. Otherwise pass</li>
<li>The resultant text component may not contain a translatable component in its hierarchy which has the same text field as this.</li>
</ol>
After formatting a component, if that component has a "continue" field, then take the text component given by that field, and repeat this process for that component. 

To replace a component with another do the following:
<ol type="1">
<li>If the original component is the top level component, then the result of formatting is the new component</li>
<li>Otherwise, set the continue field of the original components parent to the new component.</li>
<li>If the original component had a continue field, then set the continue field of the first component in the new component's hierarchy which does not have a continue field to the object which is specified by the original component's continue field  
</ol>

## Translation ##
Translatable Text Components are mapped by their translation key to a component in an implementation defined language file. The translation key is the "text" of the translatable component. 

The key is first split at every `.`. The first token is the key into the `entries` object. That key should name an object with a `tree` key, unless it is the only token. This process is repeated for the `tree` of the previous level and the next token until there are no more tokens, there is no `tree` object in the previous level, or the token does not name an object in the `tree`. (This process is known as the key search). 
When the final token is consumed, there should be a `"component"` object in the level. The translatable text component is replaced with that object. If there is no `component` object, or the key search did not find part of the translation key, then the translatable component is replaced with the raw text component which has the same `text` field as the original component. 
The Language file also specifies locale information. 

## Drawing Images ##
When an image text component is drawn, it is instead replaced with the image named by the `text` field of the component, or the portion thereof specified by the `rect` field. 



