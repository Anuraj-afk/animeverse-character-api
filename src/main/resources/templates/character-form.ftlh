<#import "layout.ftlh" as layout>

<#macro fieldError field>
    <#if errors?? && errors[field]??>
        <p id="${field}-error" role="alert">${errors[field]}</p>
    </#if>
</#macro>

<@layout.page title=formTitle>
    <main class="container">
        <h2 class="section-title">${formTitle}</h2>

        <form class="pure-form pure-form-stacked" method="post"
              action="${formAction}" novalidate>
            <#if formToken??>
                <input type="hidden" name="formToken" value="${formToken}">
            </#if>

            <label for="name">Name</label>
            <input id="name" name="name" type="text"
                   value="${character.name!''}" maxlength="100"
                   aria-describedby="name-error" required>
            <@fieldError field="name" />

            <label for="universe">Universe</label>
            <input id="universe" name="universe" type="text"
                   value="${character.universe!''}" maxlength="100"
                   aria-describedby="universe-error" required>
            <@fieldError field="universe" />

            <label for="species">Species</label>
            <input id="species" name="species" type="text"
                   value="${character.species!''}" maxlength="100"
                   aria-describedby="species-error" required>
            <@fieldError field="species" />

            <label for="description">Description</label>
            <textarea id="description" name="description" rows="5"
                      maxlength="2000" aria-describedby="description-error"
                      required>${character.description!''}</textarea>
            <@fieldError field="description" />

            <label for="imageUrl">Image URL (optional)</label>
            <input id="imageUrl" name="imageUrl" type="text"
                   value="${character.imageUrl!''}" maxlength="1024"
                   placeholder="/images/naruto.webp"
                   aria-describedby="imageUrl-help imageUrl-error">
            <small id="imageUrl-help">Enter an image link or a local path such as /images/naruto.webp.</small>
            <@fieldError field="imageUrl" />

            <div>
                <button type="submit" class="pure-button pure-button-primary">
                    ${submitLabel}
                </button>
                <a class="pure-button" href="/">Cancel</a>
            </div>
        </form>
    </main>
</@layout.page>
