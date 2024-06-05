function change() {
    // Seleziona i bottoni esistenti e nascondili
    const playButton = document.getElementById('fake-play-button');
    const exitButton = document.getElementById('fake-exit-button');
    if (playButton) playButton.style.display = 'none';
    if (exitButton) exitButton.style.display = 'none';

    // Seleziona il contenitore dei bottoni
    const buttonsContainer = document.querySelector('.buttons');
    
    // Testi dei nuovi bottoni
    const buttonLabels = ['Online', 'Multi-Player', 'Hard-AI', 'Easy-AI'];
    
    // Crea e aggiungi i nuovi bottoni
    buttonLabels.forEach(label => {
        const button = document.createElement('button');
        button.textContent = label;
        if (label === 'Online') {
            button.id = 'fake-online-button';
        } else if (label === 'Multi-Player') {
            button.id = 'fake-multi-player-button';
        } else if (label === 'Hard-AI') {
            button.id = 'fake-hard-ai-button';
        } else if (label === 'Easy-AI') {
            button.id = 'fake-easy-ai-button';
        }

        // Applica gli stili dei bottoni esistenti
        button.style.cssText = `
            align-items: center;
            border-radius: 8px;
            color: black;
            display: flex;
            font-family: sans-serif;
            font-size: 20px;
            justify-content: center;
            line-height: 1em;
            max-width: 100%;
            min-width: 140px;
            padding: 16px 24px;
            white-space: nowrap;
            cursor: pointer;
            transition: 300ms;
            background-color: whitesmoke; /* Aggiungi altri stili se necessario */
        `;
        
        // Aggiungi il bottone al contenitore
        buttonsContainer.appendChild(button);
    });
}
