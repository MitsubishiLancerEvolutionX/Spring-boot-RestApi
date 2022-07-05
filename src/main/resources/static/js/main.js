let roleList = [
    {id: 1, role: "ROLE_USER"},
    {id: 2, role: "ROLE_ADMIN"}
]
let isUser = true;

$(async function () {
    await getUser();
    await infoUser();
    await tittle();
    await getUsers();
    await getAdminRequests();
    await getNewUserForm();
    await getDefaultModal();
    await createUser();

})

const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('api/users'),
    findUserByUsername: async () => await fetch(`api/user`),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetch.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`api/users/${id}`, {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetch.head}),
    createAdminRequest: async (userId) => await fetch('api/admin-requests', {method: 'POST', headers: userFetch.head, body: JSON.stringify({"user": {"userId": userId}})}),
    findAllAdminRequests: async () => await fetch('api/admin-requests'),
    acceptRequest: async (id) => await fetch(`api/admin-requests/${id}/appoint`, {method: 'POST', headers: userFetch.head}),
    cancelRequest: async (id) => await fetch(`api/admin-requests/${id}`, {method: 'DELETE', headers: userFetch.head}),
}

async function infoUser() {
    let temp = '';
    const info = document.querySelector('#info');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
             <span style="color: white">
               ${user.login} with roles <span>${user.roles.map(e => " " + e.role.substr(5))}</span>
                </div>
                <div hidden id="userId">${user.userId}</div>
            </span>
                </tr>
            `;
        });
    info.innerHTML = temp;
}