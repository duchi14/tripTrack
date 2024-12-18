function toggleSearchForm() {
    const selectedFilter = document.querySelector('input[name="filter"]:checked').value;
    const searchForm = document.getElementById('searchForm');
    const trackSearchForm = document.getElementById('trackSearchForm');

    if (selectedFilter === 'searchForm') {
        searchForm.style.display = 'flex';
        trackSearchForm.style.display = 'none';
    } else {
        searchForm.style.display = 'none';
        trackSearchForm.style.display = 'flex';
    }
}