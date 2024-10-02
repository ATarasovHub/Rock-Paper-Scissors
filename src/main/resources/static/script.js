function playGame(playerChoice) {
    // Elemente der Auswahl des Spielers
    // Элементы выбора игрока
    const choices = document.querySelectorAll('.choice');

    // Zurücksetzen der Skalierung für alle Tasten
    // Сбрасываем масштаб для всех кнопок
    choices.forEach(choice => {
        choice.style.transform = 'scale(1)';
    });

    // Vergrößern der ausgewählten Taste
    // Увеличиваем выбранную кнопку
    const selectedChoice = document.getElementById(playerChoice);
    selectedChoice.style.transform = 'scale(1.1)';
    setTimeout(() => {
        selectedChoice.style.transform = 'scale(1)';
    }, 300);

    // Ausblenden des Textes zur Computerwahl
    // Скрываем текст ожидания выбора компьютера
    document.getElementById("computerChoiceText").innerText = "";

    // Verstecken aller Computerbilder
    // Скрываем все изображения компьютера
    const computerImages = document.querySelectorAll('.computerImage');
    computerImages.forEach(image => {
        image.style.display = 'none';
        image.style.opacity = '0'; // Zurücksetzen der Deckkraft
        // Сбрасываем непрозрачность
    });

    // Abrufen des Ergebnisses vom Server
    // Получаем результат из сервера
    fetch(`/play?choice=${playerChoice}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Abrufen des JSON-Ergebnisses
            // Получаем JSON с результатами
        })
        .then(data => {
            // Ausgabe der Ergebnisse auf den Bildschirm
            // Выводим результаты на экран
            const resultText = document.getElementById("resultText");
            const computerChoiceText = document.getElementById("computerChoiceText");

            // Zeigen der Computerwahl
            // Показываем выбор компьютера
            computerChoiceText.innerText = `Computer chose: ${data.computerChoice}`;
            resultText.innerText = `You chose ${data.playerChoice}, computer chose ${data.computerChoice}. ${data.result}`;

            // Zeigen nur des Bildes der Computerwahl
            // Показываем только изображение выбора компьютера
            const winningImageId = `computerImage${data.computerChoice.charAt(0).toUpperCase() + data.computerChoice.slice(1)}`;
            const winningImage = document.getElementById(winningImageId);
            winningImage.style.display = 'block'; // Zuerst das Bild anzeigen
            // Сначала показываем изображение
            setTimeout(() => {
                winningImage.style.opacity = '1'; // Dann die Deckkraft erhöhen
                // Затем увеличиваем непрозрачность
            }, 10); // Eine kleine Verzögerung, damit die Animation funktioniert
            // Небольшая задержка для того, чтобы анимация сработала
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            // Если возникла проблема с получением результата
            const resultText = document.getElementById("resultText");
            resultText.innerText = "Error occurred while fetching the result.";
            // Ошибка при получении результата
        });
}

// Функция для анимации кнопки "View History"
function animateViewHistoryButton() {
    const viewHistoryButton = document.querySelector('#view-history button');
    viewHistoryButton.style.transform = 'scale(1.1)';
    setTimeout(() => {
        viewHistoryButton.style.transform = 'scale(1)';
    }, 300);
}

// Добавляем обработчик события для кнопки "View History"
document.querySelector('#view-history button').addEventListener('click', animateViewHistoryButton);
