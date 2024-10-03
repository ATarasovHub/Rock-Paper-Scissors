document.addEventListener('DOMContentLoaded', fetchGameHistory);

let gameHistory = [];
let sortOrder = {
    id: 'asc',
    playerChoice: 'asc',
    computerChoice: 'asc',
    result: 'asc',
    gameDate: 'asc'
};

function fetchGameHistory() {
    fetch('/history')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            gameHistory = data;
            displayGameHistory(gameHistory);
            addSortingEventListeners();
            updateWinPercentage(); // Обновляем процент побед при загрузке истории
        })
        .catch(error => {
            console.error('Error fetching game history:', error);
        });
}

function displayGameHistory(history) {
    const historyTableBody = document.getElementById('historyTableBody');
    historyTableBody.innerHTML = '';

    history.forEach(entry => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${entry.id}</td>
            <td>${entry.playerChoice}</td>
            <td>${entry.computerChoice}</td>
            <td>${entry.result}</td>
            <td>${new Date(entry.gameDate).toLocaleString()}</td>
        `;
        historyTableBody.appendChild(row);
    });
}

function sortTable(property) {
    const sortedHistory = [...gameHistory].sort((a, b) => {
        if (property === 'gameDate') {
            return new Date(a[property]) - new Date(b[property]);
        }
        if (a[property] < b[property]) return sortOrder[property] === 'asc' ? -1 : 1;
        if (a[property] > b[property]) return sortOrder[property] === 'asc' ? 1 : -1;
        return 0;
    });

    sortOrder[property] = sortOrder[property] === 'asc' ? 'desc' : 'asc';
    displayGameHistory(sortedHistory);
    updateSortArrows(property);
}

function updateSortArrows(activeProperty) {
    const arrows = document.querySelectorAll('.sort-arrow');
    arrows.forEach(arrow => {
        const isActive = arrow.dataset.sort === activeProperty;
        arrow.textContent = isActive ? (sortOrder[activeProperty] === 'asc' ? '▲' : '▼') : '▲';
        arrow.classList.toggle('active', isActive);
    });
}

function addSortingEventListeners() {
    document.querySelector('th:nth-child(1)').addEventListener('click', () => sortTable('id'));
    document.querySelector('th:nth-child(2)').addEventListener('click', () => sortTable('playerChoice'));
    document.querySelector('th:nth-child(3)').addEventListener('click', () => sortTable('computerChoice'));
    document.querySelector('th:nth-child(4)').addEventListener('click', () => sortTable('result'));
    document.querySelector('th:nth-child(5)').addEventListener('click', () => sortTable('gameDate'));
}



function showWinPercentage() {
    fetch('http://localhost:8080/win-percentage') // Убедитесь, что порт соответствует вашему приложению
        .then(response => {
            console.log('Response status:', response.status); // Отладочный вывод
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Received data:', data); // Отладочный вывод

            // Обновляем содержимое таблицы статистики
            document.getElementById('statsWins').innerText = data.wins;
            document.getElementById('statsLosses').innerText = data.losses;
            document.getElementById('statsDraws').innerText = data.draws;
            document.getElementById('statsTotalGames').innerText = data.totalGames;
            document.getElementById('statsWinPercentage').innerText = data.winPercentage.toFixed(2) + '%';
        })
        .catch(error => {
            console.error('Error fetching win percentage:', error);
            document.getElementById('winPercentageText').innerText = 'Error fetching win percentage';
        });
}


// Функция для возврата на главную страницу
function goBack() {
    window.location.href = 'index.html';
}

document.getElementById("clearHistoryButton").addEventListener("click", clearHistory);

function clearHistory() {
    fetch('/clear-history', { method: 'DELETE' }) // Убедитесь, что путь правильный
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // Ожидаем текст, а не JSON
        })
        .then(message => {
            const resultMessage = document.getElementById("resultMessage");
            resultMessage.innerText = message; // Устанавливаем текст сообщения
            resultMessage.style.color = "green";
            resultMessage.style.opacity = "1";
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            const resultMessage = document.getElementById("resultMessage");
            resultMessage.innerText = "History cleared. Please refresh the page!"; // Сообщение по умолчанию
            resultMessage.style.color = "green";
            resultMessage.style.opacity = "1";
        });
}




