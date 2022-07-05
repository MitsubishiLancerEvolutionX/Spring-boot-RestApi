async function acceptRequest(id) {
    await userFetch.acceptRequest(id);
    await userFetch.cancelRequest(id)
    await getAdminRequests();
    await getUsers();
}