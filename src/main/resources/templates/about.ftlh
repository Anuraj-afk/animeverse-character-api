<#import "layout.ftlh" as layout>

<@layout.page title="About">
    <section class="hero">
        <h2>About AnimeVerse</h2>
        <p>Explore characters from across the world of anime.</p>
    </section>

    <main class="container">
        <h2 class="section-title">The Character Gallery</h2>
        <img class="pure-img" src="/images/goku.webp" alt="Goku from Dragon Ball" width="240">

        <p>
            AnimeVerse began as a gallery of five characters from Dragon Ball,
            Naruto, One Piece, Jujutsu Kaisen, and Bleach. The gallery brings
            characters from different anime together so visitors can browse by
            universe and learn what makes each character unique.
        </p>

        <h2>How AnimeVerse Is Built</h2>
        <p>
            Spring MVC handles page requests and FreeMarker renders the HTML
            using a shared layout. The character pages call CharacterService,
            which uses a Spring Data JPA repository to read and write records
            in Neon PostgreSQL. The REST API uses the same service to return
            character data as JSON.
        </p>
    </main>
</@layout.page>
