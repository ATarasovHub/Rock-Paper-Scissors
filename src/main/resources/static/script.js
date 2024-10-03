function playGame(playerChoice) {
    // Элементы выбора игрока
    const choices = document.querySelectorAll('.choice');

    // Сбрасываем масштаб для всех кнопок
    choices.forEach(choice => {
        choice.style.transform = 'scale(1)';
    });

    // Увеличиваем выбранную кнопку
    const selectedChoice = document.getElementById(playerChoice);
    selectedChoice.style.transform = 'scale(1.1)';
    setTimeout(() => {
        selectedChoice.style.transform = 'scale(1)';
    }, 300);

    // Скрываем текст ожидания выбора компьютера
    document.getElementById("computerChoiceText").innerText = "";

    // Скрываем все изображения компьютера
    const computerImages = document.querySelectorAll('.computerImage');
    computerImages.forEach(image => {
        image.style.display = 'none';
        image.style.opacity = '0'; // Сбрасываем непрозрачность
    });

    // Получаем результат из сервера
    fetch(`/play?choice=${playerChoice}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Получаем JSON с результатами
        })
        .then(data => {
            // Выводим результаты на экран
            const resultText = document.getElementById("resultText");
            const computerChoiceText = document.getElementById("computerChoiceText");

            // Показываем выбор компьютера
            computerChoiceText.innerText = `Computer chose: ${data.computerChoice}`;
            resultText.innerText = `You chose ${data.playerChoice}, computer chose ${data.computerChoice}. ${data.result}`;

            // Показываем только изображение выбора компьютера
            const winningImageId = `computerImage${data.computerChoice.charAt(0).toUpperCase() + data.computerChoice.slice(1)}`;
            const winningImage = document.getElementById(winningImageId);
            winningImage.style.display = 'block'; // Сначала показываем изображение
            setTimeout(() => {
                winningImage.style.opacity = '1'; // Затем увеличиваем непрозрачность
            }, 10); // Небольшая задержка для того, чтобы анимация сработала
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            const resultText = document.getElementById("resultText");
            resultText.innerText = "Error occurred while fetching the result.";
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

// Новая функция для генерации 100 матчей
function generate100Matches() {
    fetch('/generate-100-matches')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // Получаем текст ответа
        })
        .then(message => {
            const resultMessage = document.getElementById("resultMessage");
            resultMessage.innerText = message; // Устанавливаем текст сообщения
            resultMessage.style.display = 'none'; // Скрываем элемент перед показом

            // Плавно показываем сообщение
            setTimeout(() => {
                resultMessage.style.display = 'block'; // Показываем элемент
                resultMessage.style.opacity = '0'; // Начальная непрозрачность
                resultMessage.style.transition = 'opacity 0.5s ease'; // Плавный переход
                setTimeout(() => {
                    resultMessage.style.opacity = '1'; // Конечная непрозрачность
                }, 10); // Небольшая задержка для начала анимации
            }, 100); // Задержка перед показом
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            const resultMessage = document.getElementById("resultMessage");
            resultMessage.innerText = "Error occurred while generating 100 matches.";
            resultMessage.style.display = 'none'; // Скрываем элемент перед показом

            // Плавно показываем сообщение
            setTimeout(() => {
                resultMessage.style.display = 'block'; // Показываем элемент
                resultMessage.style.opacity = '0'; // Начальная непрозрачность
                resultMessage.style.transition = 'opacity 0.5s ease'; // Плавный переход
                setTimeout(() => {
                    resultMessage.style.opacity = '1'; // Конечная непрозрачность
                }, 10); // Небольшая задержка для начала анимации
            }, 100); // Задержка перед показом
        });
}
