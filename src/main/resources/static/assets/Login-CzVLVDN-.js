import{_ as c,r as f,a as p,b as g,d as t,w,i as r,v as a,u as b,o as v,l as I}from"./index-mjseeehd.js";const y={class:"login"},_={class:"container"},P={class:"form-floating"},x={class:"form-floating"},k={__name:"Login",setup(B){const s=f({form:{loginId:"",loginPw:""}}),d=b(),m=p(),u=async()=>{var o,e,l,i;if((o=s.form.loginId)!=null&&o.trim()){if(!((l=s.form.loginPw)!=null&&l.trim())){window.alert("패스워드를 입력해주세요."),(i=document.getElementById("loginPw"))==null||i.focus();return}}else{window.alert("이메일을 입력해주세요."),(e=document.getElementById("loginId"))==null||e.focus();return}const n=await I(s.form);switch(n.status){case 200:m.setAccessToken(n.data),await d.push("/");break;case 404:window.alert("입력하신 정보와 일치하는 회원이 없습니다.");break}};return(n,o)=>(v(),g("div",y,[t("div",_,[t("form",{class:"py-5 d-flex flex-column gap-3",onSubmit:w(u,["prevent"])},[o[4]||(o[4]=t("h1",{class:"h5 mb-3"},"로그인",-1)),t("div",P,[r(t("input",{type:"email",class:"form-control",id:"loginId",placeholder:"이메일","onUpdate:modelValue":o[0]||(o[0]=e=>s.form.loginId=e)},null,512),[[a,s.form.loginId]]),o[2]||(o[2]=t("label",{for:"loginId"},"이메일",-1))]),t("div",x,[r(t("input",{type:"password",class:"form-control",id:"loginPw",placeholder:"패스워드","onUpdate:modelValue":o[1]||(o[1]=e=>s.form.loginPw=e)},null,512),[[a,s.form.loginPw]]),o[3]||(o[3]=t("label",{for:"loginPw"},"패스워드",-1))]),o[5]||(o[5]=t("button",{type:"submit",class:"w-100 h6 btn py-3 btn-primary"},"로그인",-1))],32)])]))}},S=c(k,[["__scopeId","data-v-b6e28cbe"]]);export{S as default};
