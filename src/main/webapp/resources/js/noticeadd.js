// 취소 버튼 클릭 시 목록 페이지 이동
document.querySelector('.notice-cancel').addEventListener('click', () => {
    location.href = '/';
});

window.addEventListener('DOMContentLoaded', function () {
	document.getElementById('subject-name').focus();
});

function init() {
	document.getElementById('subject-name').value = "";
	document.getElementById('file-input').value = "";
	const name = document.getElementById('subject-name').value;	
	document.getElementById('subject-name').focus();
	
}
// 저장 버튼 클릭
document.querySelector('.notice-add').addEventListener('click', () => {
	const subject = document.getElementById('subject-name').value;
	const file = document.getElementById('file-input');
	const formData = new FormData();
	if(subject === '' || subject === null){
		alert("제목을 입력하세요.");
		init();
		return;
	}if(file.files[0] === undefined || file.files[0] === null){
		alert("파일을 선택해 주세요.");
		init();
		return;
	}
	const extension = file.files[0].name.substring(file.files[0].name.indexOf(".") + 1);
	console.log("extension ==>", extension);
	if(extension !== 'jpg'){ // || extension !== 'png'
		alert("확장자는 jpg 또는 png 이여야 합니다.");
		init();
		return;
	}
	
	const filesize = file.files[0].size;
	console.log("filesize ==>", filesize);
	if(filesize > 5242880){
		alert("파일 용량은 5MB보다 낮아야 합니다.");
		init();
		return;
	}
	console.log("file.files[0] ==>", file.files[0]);
	formData.append('noticeFile', file.files[0]);
    formData.append('subject', subject);
	console.log("formData ==>", formData);
   $.ajax({
       url: '/notice/register',
       data: formData,
       enctype: 'multipart/form-data',
       processData: false,
       contentType: false,
       type: 'POST',
       success: function (res) {
           console.log("res ==>", res);
		   location.href = '/notice/noticelist';
       },error: function (xhr) {
       	   console.log("xhr ==>", xhr);
	   }
   });


    // axios.post(`/notice/register`, formData).then(res => {
    // 	location.href = '/';
	// });
});