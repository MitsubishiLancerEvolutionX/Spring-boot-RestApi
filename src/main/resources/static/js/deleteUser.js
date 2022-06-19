async function deleteUser(id) {
    await userFetch.deleteUser(id);
    await getUsers();
}