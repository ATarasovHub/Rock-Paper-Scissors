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
        arrow.textContent = isActive ? (sortOrder[activeProperty] === 'asc' ? '▲' : '▼') : '▲'; // Устанавливаем направление для активного заголовка и оставляем стрелки для остальных
        arrow.classList.toggle('active', isActive); // Устанавливаем класс активности
    });
}

function addSortingEventListeners() {
    document.querySelector('th:nth-child(1)').addEventListener('click', () => sortTable('id'));
    document.querySelector('th:nth-child(2)').addEventListener('click', () => sortTable('playerChoice'));
    document.querySelector('th:nth-child(3)').addEventListener('click', () => sortTable('computerChoice'));
    document.querySelector('th:nth-child(4)').addEventListener('click', () => sortTable('result'));
    document.querySelector('th:nth-child(5)').addEventListener('click', () => sortTable('gameDate'));
}

function goBack() {
    window.location.href = 'index.html';
}
