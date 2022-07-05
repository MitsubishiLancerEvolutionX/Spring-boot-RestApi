async function getUser() {
    let temp = '';
    const table = document.querySelector('#tableUser tbody');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {

            $(function (){
                let role = ""
            for (let i = 0; i < user.roles.length; i++) {
                role = user.roles[i].role
                if (role === "ROLE_ADMIN") {
                    isUser = false;
                }
            }
            if (isUser) {
            $("#userTable").addClass("show active");
            $("#userTab").addClass("show active");
                temp = `
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.login}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(e => " " + e.role.substr(5))}</td>
                    
                    <button type="button" ${user.requestAdmins?'disabled':''} class="button-cancel btn btn-danger" id="wantAdmin">I want to be an admin</button>
                       
                </tr>
            `;
                table.innerHTML = temp;

                $("#wantAdmin").on('click', (event) => {
                    let targetButton = $(event.target);
                    let buttonRequestId = $("#userId").text();
                    userFetch.createAdminRequest(Number(buttonRequestId));
                    setTimeout(function(){
                        getUser();
                    }, 100);
                })

            } else {
                temp = `
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.login}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(e => " " + e.role.substr(5))}</td>       
                </tr>
            `;
                table.innerHTML = temp;
            $("#adminTable").addClass("show active");
            $("#adminTab").addClass("show active");
            }
            })
        })
}

async function tittle() {
    let temp = ''
    const h1a1 = document.querySelector('#h1a1');
    if (isUser) {
        temp = `
            <h1 className="h1 a1" id="h1a1">User information page</h1>
            `;
        h1a1.innerHTML = temp;
    } else {
        temp = `
            <h1 className="h1 a1" id="h1a1">Admin panel</h1>
            `;
        h1a1.innerHTML = temp;
    }
}
async function getAdminRequests() {
    let temp = '';
    const table = document.querySelector('#tableRequestsAllUsers tbody');
    await userFetch.findAllAdminRequests()
        .then(res => res.json())
        .then(requests => {
            requests.forEach(request => {
                temp += `
                <tr>
                    <td>${request.id}</td>
                    <td>${request.user.login}</td>
                    <td>${request.user.firstName}</td>
                    <td>${request.user.lastName}</td>
                    <td>
                        <button type="button" data-requestid="${request.id}" data-action="accept" class="button-accept btn btn-info"
                            className data-toggle="modal" data-target="#acceptModal">Accept</button>
                    </td>
                    <td>
                        <button type="button" data-requestid="${request.id}" data-action="cancel" class="button-cancel btn btn-danger"
                            className data-toggle="modal" data-target="#cancelModal">Cancel</button>
                    </td>
                </tr>
               `;
            })
            table.innerHTML = temp;
        })

    $("#tableRequestsAllUsers").find('.button-accept').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonRequestId = targetButton.attr('data-requestid');
        acceptRequest(buttonRequestId);
    })

    $("#tableRequestsAllUsers").find('.button-cancel').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonRequestId = targetButton.attr('data-requestid');
        cancelRequest(buttonRequestId);
    })
}
async function getUsers() {
    let temp = '';
    const table = document.querySelector('#tableAllUsers tbody');
    await userFetch.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                temp += `
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.login}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(e => " " + e.role.substr(5))}</td>
                    <td>
                        <button type="button" data-userid="${user.userId}" data-action="edit" class="button-edit btn btn-info"
                            className data-toggle="modal" data-target="#editModal">Edit</button>
                    </td>
                    <td>
                        <button type="button" data-userid="${user.userId}" data-action="delete" class="button-delete btn btn-danger"
                            className data-toggle="modal" data-target="#deleteModal">Delete</button>
                    </td>
                </tr>
               `;
            })
            table.innerHTML = temp;

        })

    $("#tableAllUsers").find('.button-edit').on('click', (event) => {
        let defaultModal = $('#defaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })

    $("#tableAllUsers").find('.button-delete').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        deleteUser(buttonUserId);
    })
}

async function getNewUserForm() {
    let button = $(`#addUser`);
    let form = $(`#addForm`)
    button.on('click', () => {
        form.show()
    })
}

async function getDefaultModal() {
    $('#defaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}