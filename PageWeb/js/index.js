"use strict";
document.addEventListener('DOMContentLoaded', () => {
    function getColorAtPercentage(percentage) {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = 1;
        canvas.height = 1;
        // Logique pour obtenir la couleur en fonction du pourcentage...
        return ''; // Retourner la couleur calculée (au format RGB par exemple)
    }
    function updateInputBackgroundColor(color) {
        const inputParametres = document.querySelectorAll('[name^="parametre"]');
        inputParametres.forEach((input) => {
            input.style.backgroundColor = color;
        });
    }
    function updateColors() {
        const currentPercentage = getCurrentPercentage();
        const color = getColorAtPercentage(currentPercentage);
        const boite = document.querySelector('.boite');
        const rectangle = document.querySelector('.rectangle');
        if (boite && rectangle) {
            boite.style.backgroundColor = color;
            rectangle.style.backgroundColor = color;
            updateInputBackgroundColor(color);
        }
    }
    document.addEventListener('animationiteration', () => {
        updateColors();
    });
    function getCurrentPercentage() {
        const animationDuration = 10000;
        const currentTime = performance.now();
        const elapsedTime = currentTime % animationDuration;
        const percentage = (elapsedTime / animationDuration) * 100;
        return percentage;
    }
    const formulaireParametres = document.getElementById('formulaireParametres');
    formulaireParametres.addEventListener('submit', (event) => {
        event.preventDefault();
        const nombreParametres = parseInt(document.getElementById('nombreParametres').value, 10);
        const listeParametres = document.getElementById('listeParametres');
        if (listeParametres) {
            listeParametres.innerHTML = '';
            for (let i = 1; i <= nombreParametres; i++) {
                ajouterParametre(i);
            }
        }
    });
    function ajouterParametre(numero) {
        const divParametre = document.createElement('div');
        const labelParametre = document.createElement('label');
        labelParametre.textContent = `Paramètre ${numero} :`;
        const inputParametre = document.createElement('input');
        inputParametre.type = 'text';
        inputParametre.name = `parametre${numero}`;
        inputParametre.placeholder = 'Nom du paramètre';
        divParametre.appendChild(labelParametre);
        divParametre.appendChild(inputParametre);
        const listeParametres = document.getElementById('listeParametres');
        if (listeParametres) {
            listeParametres.appendChild(divParametre);
        }
    }
    formulaireParametres.addEventListener('submit', (event) => {
        event.preventDefault();
        const nombreParametres = parseInt(document.getElementById('nombreParametres').value, 10);
        const listeParametres = document.getElementById('listeParametres');
        if (listeParametres) {
            listeParametres.innerHTML = '';
            for (let i = 1; i <= nombreParametres; i++) {
                ajouterParametre(i);
            }
        }
    });
    document.getElementById('boutonEnvoyer').addEventListener('click', () => {
        const inputsParametres = document.querySelectorAll('[name^="parametre"]');
        let parametres = [];
        inputsParametres.forEach((input, index) => {
            const nomParametre = input.value || '';
            if (nomParametre) {
                parametres.push(` ${nomParametre}`);
            }
            else {
                alert('Vous devez saisir des données valides.');
            }
        });
        const chaineParametres = parametres.join('');
        console.log(chaineParametres);
    });
});
