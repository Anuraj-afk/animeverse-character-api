<#import "layout.ftlh" as layout>

<@layout.page title=character.name>
    <#assign imageUrl = (character.imageUrl!"")?trim>
    <#if !imageUrl?has_content>
        <#assign imageUrl = "/images/placeholder.svg">
    </#if>

    <main class="container">
        <h2 class="section-title">${character.name}</h2>

        <img class="pure-img" src="${imageUrl}"
             alt="Portrait of ${character.name}" width="400">

        <dl>
            <dt><strong>Universe</strong></dt>
            <dd>${character.universe}</dd>

            <dt><strong>Species</strong></dt>
            <dd>${character.species}</dd>
        </dl>

        <h3>Description</h3>
        <p>${character.description}</p>

        <a class="pure-button" href="/">Back to Gallery</a>
    </main>
</@layout.page>
