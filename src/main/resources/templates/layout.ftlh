<#macro page title>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} | AnimeVerse</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@3.1.0/build/pure-min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>⚡ AnimeVerse</h1>
            <nav class="pure-menu pure-menu-horizontal" aria-label="Main navigation">
                <ul class="pure-menu-list">
                    <li class="pure-menu-item">
                        <a class="pure-menu-link" href="/">Home</a>
                    </li>
                    <li class="pure-menu-item">
                        <a class="pure-menu-link" href="/about">About</a>
                    </li>
                    <li class="pure-menu-item">
                        <a class="pure-menu-link" href="/characters/new">Add a Character</a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
        <#if successMessage??>
        <div class="container">
        <p role="status">${successMessage}</p>
        </div>
        </#if>
    <#nested>
</body>
</html>
</#macro>
