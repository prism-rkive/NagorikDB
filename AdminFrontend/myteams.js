document.addEventListener("DOMContentLoaded", async function () {
    try {
        const a_id = sessionStorage.getItem('a_id');
    
        if (!a_id) {
            console.error('Not logged in');
            return;
        }
        fetch(`http://localhost:8080/api/area/teams/${a_id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            populateTable(data);
        })
        .catch(error => {
            console.error('Error fetching rescue teams:', error);
        });


    function populateTable(teams) {
    const tableBody = document.getElementById('teams-table-body');
    tableBody.innerHTML = ''; // Clear any existing rows

    teams.forEach(team => {
        const row = document.createElement('tr');

        const teamIdCell = document.createElement('td');
        teamIdCell.textContent = team.team_id;
        row.appendChild(teamIdCell);

        const teamNameCell = document.createElement('td');
        teamNameCell.textContent = team.name;
        row.appendChild(teamNameCell);

        const availabilityCell = document.createElement('td');
        const statusButton = document.createElement('button');
        statusButton.textContent = team.availability;
        statusButton.className = `status-button ${team.availability.toLowerCase()}`;
        statusButton.disabled = true; // Prevent interaction
        availabilityCell.appendChild(statusButton);
        row.appendChild(availabilityCell);

        tableBody.appendChild(row);
    });
}
} catch (err) {
    console.error("Error fetching surveys:", err);
    alert("An error occurred while fetching surveys.");
}
});

