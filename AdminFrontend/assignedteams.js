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

    teams
    .filter(team => team.availability.toLowerCase() === 'assigned')
    .forEach(team => {
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
        statusButton.addEventListener('mouseover', () => {
            statusButton.textContent = 'Change Status';
        });
        statusButton.addEventListener('mouseout', () => {
            statusButton.textContent = team.availability;
        });
        statusButton.addEventListener('click', () => {
            changeStatus(team.team_id,a_id, statusButton);
        });
        availabilityCell.appendChild(statusButton);
        row.appendChild(availabilityCell);

        tableBody.appendChild(row);
    });
}

function changeStatus(teamId,a_id,button) {
    const newStatus = button.textContent === 'assigned' ? 'available' : 'assigned';

    fetch(`http://localhost:8080/api/rescue/avail/${a_id}/${teamId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ availability: newStatus }), // Include the JSON payload
        //body: JSON.stringify({ availability: newStatus }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to assign team! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(responseText => {
            button.textContent = newStatus;
            button.className = `status-button ${newStatus.toLowerCase()}`;
            alert(`Team ${teamId} status changed to Available`);
        })
        .catch(error => {
            console.error('Error assigning rescue team:', error);
            alert('An error occurred while assigning the rescue team.');
        });

}
    }
    catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }})
    