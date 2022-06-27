async function cancelRequest(id) {
    await userFetch.cancelRequest(id);
    await getAdminRequests();
}