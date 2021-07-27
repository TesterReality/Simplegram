<template>
    <v-app>
        <v-main>
            <v-container class="fill-height" fluid>
                <v-row align="center" justify="center" dense>
                    <v-col cols="12" sm="8" md="4" lg="4">
                        <v-card elevation="0">
                            <a href="https://edu-fedorae.netlify.app" name="Fedorae Education" title="Fedorae Education"
                               target="_blank">
                                <v-img src="@/assets/simplegram.png" alt="Fedorae Education Log" contain
                                       height="200"></v-img>
                            </a>
                        </v-card>
                        <v-card class="mx-auto" max-width="500">
                            <v-card-title class="title font-weight-regular justify-space-between">
                                <span>Регистрация</span>
                            </v-card-title>
                            <v-window>
                                <v-window-item>
                                    <v-card-text>
                                        <v-card-text class="text-center" >

                                        <div
                                                v-if="message"
                                                class="alert"
                                                :class="successful ? 'alert-success' : 'alert-danger'"
                                        >{{message}}</div>
                                        </v-card-text>

                                        <v-form name="form" @submit.prevent >
                                            <v-text-field v-model="user.login"
                                                          v-validate="'required|min:3|max:20'"
                                                          label="Введите Ваш логин" prepend-inner-icon="mdi-account"
                                                          name="login"
                                                          type="text" class="rounded-lg" outlined @change="loginChange"></v-text-field>

                                            <div
                                                    v-if="submitted && errors.has('login')"
                                                    class="alert-danger"
                                            >{{errors.first('login')}}</div>



                                            <v-text-field v-model="user.username"
                                                          v-validate="'required|min:3|max:20'"
                                                          label="Введите Ваше Имя"
                                                          name="username"
                                                          prepend-inner-icon="mdi-rename-box"
                                                          type="text" class="rounded-lg" outlined></v-text-field>

                                            <v-card-text class="text-center"
                                                         v-if="submitted && errors.has('username')"
                                            >
                                                <span class="caption grey--text text--darken-1">
                                                    {{errors.first('username')}}
                                                </span>
                                            </v-card-text>

                                            <v-text-field v-model="user.password"
                                                          v-validate="'required|min:6|max:40'"
                                                          label="Введите Ваш пароль" name="password" prepend-inner-icon="mdi-lock"
                                                          type="password" class="rounded-lg" outlined></v-text-field>
                                            <v-text-field label="Повторите пароль" name="password1"
                                                          prepend-inner-icon="mdi-lock-outline" type="password" class="rounded-lg"
                                                          outlined></v-text-field>
                                            <div
                                                    v-if="submitted && errors.has('password')"
                                                    class="alert-danger"
                                            >{{errors.first('password')}}</div>

                                            <v-card-text class="text-left">
                                    <span class="caption grey--text text--darken-1">
                                        Загрузите изображение профиля
                                    </span>
                                            </v-card-text>
                                            <v-flex>
                                                <v-card-actions>
                                                    <v-card-text class="text-left">
                                                        <v-btn raised class="primary" @click="onClickFile">Выберите файл</v-btn>
                                                        <input
                                                                type="file"
                                                                style="display: none"
                                                                ref="fileInput"
                                                                accept="image/*"
                                                                @change="onFilePick"
                                                        >
                                                    </v-card-text>
                                                    <v-card-text class="text-right">

                                                        <v-avatar size="60">
                                                            <img :src="imageURL">

                                                        </v-avatar>
                                                    </v-card-text>
                                                </v-card-actions>
                                            </v-flex>
                                            <v-btn class="rounded-lg" color="#000000" x-large block dark @click="handleRegister">
                                                Зарегистрироваться</v-btn>
                                            <v-card-actions class="text--secondary">
                                                <v-spacer></v-spacer>
                                                <!-- <router-link :to="{ name: 'SignUp' }">Sign Up</router-link> -->
                                                <v-card-text>
                                                    Уже зарегистрированы? <a href="/login" class="pl-2" style="color: #000000">Войти</a>
                                                </v-card-text>
                                            </v-card-actions>
                                        </v-form>

                                    </v-card-text>
                                </v-window-item>
                            </v-window>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>

</template>

<script>
    import User from '../models/user';

    export default {
        name: 'Registration',
        data() {
            return {
                user: new User('', '', ''),
                submitted: false,
                successful: false,
                message: '',

                imageURL: '',
                image: null,
                loginText: '',
                isUserLoadImage: false,
                filedata: null,
                formdata:{}
            }
        },
        mounted() {
            if (this.loggedIn) {
                this.$router.push('/');
            }
        },
        methods: {
            onClickFile() {
                this.$refs.fileInput.click();
            },
            onFilePick(event) {
                const file = event.target.files;
                let filename = file[0].name;


                if (filename.lastIndexOf('.') <= 0) {
                    return alert('Пожалуйста, добавьте корректный файл.')
                }
                const fileReader = new FileReader();
                fileReader.addEventListener('load', () => {
                    this.imageURL = fileReader.result;
                });
                this.filedata= fileReader.readAsDataURL(file[0]);
                this.image = file[0];
                this.isUserLoadImage = true;

                this.formdata = new FormData();
                let fileFromDate = event.target.files[0];
                this.formdata.append("file",fileFromDate);
                console.log ( "File", this.formdata, JSON.stringify ({ 'file': fileFromDate}));
                console.log(this.formdata);


            },
            loginChange(event) {
                console.log(event);

                if(this.isUserLoadImage == false)
                {
                    this.imageURL='https://api.multiavatar.com/'+event+'.png';
                }
            },
            handleRegister() {
                console.log("Пытаемся отправить запрос на сервер");
                this.message = '';
                this.submitted = true;
                this.$validator.validate().then(isValid => {
                    console.log("Отправляем запрос....");

                    if (isValid) {
                        if(!this.isUserLoadImage)//если пользоватей не загрузил аватарку, ставим с сайта
                        {
                            //значит передает ссылку а бек разбирается
                            this.formdata = new FormData();
                            this.formdata.append('imgUrl',this.imageURL);
                        }
                       this.formdata.append('username',this.user.username);
                       this.formdata.append('login',this.user.login);
                        this.formdata.append('password',this.user.password);
                        console.log("Перед Добавили урл");


                        console.log(this.formdata);

                        this.$store.dispatch('auth/register',this.formdata).then(
                            data => {
                                this.message = data.message;
                                this.successful = true;
                            },
                            error => {
                                this.message =
                                    (error.response && error.response.data && error.response.data.message) ||
                                    error.message ||
                                    error.toString();
                                this.successful = false;
                            }
                        );
                    }else {
                        console.log("Не пройдена валидация");

                    }
                });
            }
        }
    }
</script>

<style lang="css" scoped>
</style>