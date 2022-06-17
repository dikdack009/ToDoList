$(function(){

    var check = 0;
    const myArray = [];

    const appendTask = function(data){
        var taskCode = '<a href="#" id="task" class="task-link" data-id="' +
            data.id + '">' + data.title +
            '</a><button class="delete-task" data-id="' + data.id + '">X</button>' +
            '<button class="update-task" data-id="' + data.id + '">U</button>';
        $('#todo-list')
            .append('<div class="n">' + taskCode + '</div>');
    };

   //  // Loading books on load page
   // $.get('/tasks/', function(response)
   // {
   //     for(let i in response) {
   //         appendTask(response[i]);
   //     }
   // });

    //Show adding task form
    $('#show-add-task-form').click(function(){
        $('#todo-form').css('display', 'flex');
    });

    //Delete task
    $(document).on('click', '.delete-task', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/tasks/' + taskId,
            success: function()
            {
                location.reload();
            },
            error: function(response)
            {
                if(response.status === 404) {
                    alert('Задача не найдена!');
                }
            }
        });
        return false;
    });

    //Update task
    $(document).on('click', '.update-task', function(){
        check = 1;
        var link = $(this);
        var taskId = link.data('id');
        $('#todo-form').css('display', 'flex');
        $('#save-task').click(function()
        {
            if (check === 1) {
                var data = $('#todo-form form').serialize();
                $.ajax({
                    method: "PUT",
                    url: '/tasks/' + taskId,
                    data: data,
                    success: function (response) {
                        $('#todo-form').css('display', 'none');
                        var task = {};
                        task.id = response;
                        var dataArray = $('#todo-form form').serializeArray();
                        for (let i in dataArray) {
                            task[dataArray[i]['name']] = dataArray[i]['value'];
                        }
                        appendTask(task);
                        location.reload();
                    },
                    error: function(response)
                    {
                        if(response.status === 404) {
                            alert('Задача не найдена!');
                        }
                    }
                });
            }
            check = 0;
            return false;
        });
    });

    //Closing adding task form
    $('#todo-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
            document.getElementsByTagName("input").value = "";
        }
    });

    //Getting task
    $(document).on('click', '.task-link', function(){
        var link = $(this);
        var taskId = link.data('id');
        console.log(myArray);
        console.log(myArray.indexOf(taskId));
        if(myArray.indexOf(taskId) === -1 || myArray.length === 0) {
            $.ajax({
                method: "GET",
                url: '/tasks/' + taskId,
                success: function (response) {
                    var code = '<br><span data-id='+ taskId +'><span class="t">Задача: </span>' + response.name + '</span><br>';
                    link.parent().append(code);
                    code = '<span data-id='+ taskId +'><span class="t">Дата:   </span>' + response.date + '</span><br>';
                    link.parent().append(code);
                    myArray.push(taskId);
                },
                error: function (response) {
                    if (response.status === 404) {
                        alert('Задача не найдена!');
                    }
                }
            });
        } else {
            myArray.splice(myArray.indexOf(taskId), 1);
            let regex = new RegExp('<br><span data-id=' + taskId + '><span class=\"t\">Задача: <\/span>.* <\/span><br>.*<\/span>');
            document.body.innerHTML = document.body.innerHTML.replace(regex,"");
            console.log(regex);
            regex = new RegExp('<br><span data-id=' + taskId + '><span class=\"t\">Дата:   <\/span>.* <\/span><br>.*<\/span>');
            document.body.innerHTML = document.body.innerHTML.replace(regex,"");
            console.log(regex);
            location.reload();
        }
        return false;
    });

    //Adding task
    $('#save-task').click(function()
    {
        if (check !== 1) {
            var data = $('#todo-form form').serialize();
            $.ajax({
                method: "POST",
                url: '/tasks/',
                data: data,
                success: function (response) {
                    $('#todo-form').css('display', 'none');
                    var task = {};
                    task.id = response;
                    var dataArray = $('#todo-form form').serializeArray();
                    for (let i in dataArray) {
                        task[dataArray[i]['name']] = dataArray[i]['value'];
                    }
                    appendTask(task);
                }
            });
        }
        return false;
    });

    //Show adding task form
    $('#delete-todolist').click(function(){
        $.ajax({
            method: "DELETE",
            url: '/tasks/',
            success: function()
            {
                location.reload();
            },
            error: function(response)
            {
                if(response.status === 404) {
                    alert('Задача не найдена!');
                }
            }

        });
        return false;
    });

});