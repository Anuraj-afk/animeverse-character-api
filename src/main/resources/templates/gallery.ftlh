<#import "layout.ftlh" as layout>

<@layout.page title="Character Gallery">
    <section class="hero">
        <h2>Welcome to AnimeVerse</h2>
        <p>Explore some of the most powerful and legendary characters in anime.</p>
    </section>

    <main class="container">
        <h2 class="section-title">Character Gallery</h2>

        <form class="pure-form search-form" method="get" action="/">
            <label for="name">Name</label>
            <input id="name" name="name" type="search" class="search-input"
                   value="${name}" placeholder="Search by character name">

            <label for="universe">Universe</label>
            <select id="universe" name="universe">
                <option value="">All universes</option>
                <#list universes as universe>
                    <option value="${universe}"<#if universe?lower_case == selectedUniverse?trim?lower_case> selected</#if>>
                        ${universe}
                    </option>
                </#list>
            </select>

            <button type="submit" class="pure-button pure-button-primary">Search</button>
            <a class="pure-button" href="/">Clear</a>
        </form>

        <#if characters?has_content>
            <div class="character-grid">
                <#list characters as character>
                    <#assign imageUrl = (character.imageUrl!"")?trim>
                    <#if !imageUrl?has_content>
                        <#assign imageUrl = "/images/placeholder.svg">
                    </#if>

                    <article class="character-card">
                        <img src="${imageUrl}" alt="Portrait of ${character.name}" loading="lazy">

                        <div class="card-content">
                            <h3>${character.name}</h3>
                            <p><strong>Universe:</strong> ${character.universe}</p>
                            <p><strong>Species:</strong> ${character.species}</p>
                            <a class="pure-button pure-button-primary"
                               href="/characters/${character.characterId?c}">View Details</a>
                        </div>
                    </article>
                </#list>
            </div>
        <#else>
            <p>No characters found. Try a different name or universe.</p>
        </#if>
    </main>
</@layout.page>
