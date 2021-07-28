<template>
    <v-app>
        <v-main>
            <v-container class="fill-height" fluid>
                <v-row align="center" justify="center" dense>
                    <v-col cols="12" sm="8" md="4" lg="4">
                        <v-card elevation="0">
                            <a href="https://github.com/TesterReality/Simplegram" name="Simplegram" title="Simplegram"
                               target="_blank">
                                <v-img src="@/assets/simplegram.png" alt="Simplegram Logo" contain height="200"></v-img>
                            </a>
                            <v-card-text>
                                <v-form name="form" @submit.prevent>
                                    <v-text-field
                                            label="Введите логин"
                                            name="login"
                                            v-model="user.login"
                                            v-validate="'required'"
                                            prepend-inner-icon="mdi-account"
                                            type="login"
                                            class="rounded-lg"
                                            outlined></v-text-field>
                                    <div
                                            v-if="errors.has('username')"
                                            class="alert alert-danger"
                                            role="alert"
                                    >Введите имя пользователя!
                                    </div>
                                    <v-text-field
                                            label="Введите пароль"
                                            name="password"
                                            v-model="user.password"
                                            v-validate="'required'"
                                            prepend-inner-icon="mdi-lock"
                                            type="password"
                                            class="rounded-lg"
                                            outlined></v-text-field>
                                    <div
                                            v-if="errors.has('password')"
                                            class="alert alert-danger"
                                            role="alert"
                                    >Введите пароль!
                                    </div>
                                    <v-btn class="rounded-lg" color="#000000" x-large block dark @click="handleLogin">
                                        Войти
                                    </v-btn>
                                    <v-card-actions class="text--secondary" align="center">
                                        <!-- <router-link :to="{ name: 'SignUp' }">Sign Up</router-link> -->
                                        <v-card-text>
                                            <router-link v-if="currentUser" to="/user" class="nav-link">
                                                {{ currentUser.username }}
                                            </router-link>
                                            Нет аккаунта?
                                            <router-link to="/registration" class="pl-2" style="color: #000000">
                                                Зарегистрироваться
                                            </router-link>
                                        </v-card-text>
                                    </v-card-actions>
                                </v-form>
                            </v-card-text>
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
        name: 'Login',
        data() {
            return {
                user: new User('', '',''),
                loading: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }

        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/');
            }
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            },
            handleLogin() {
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }
                    if (this.user.login && this.user.password) {
                        this.$store.dispatch('auth/login', this.user).then(
                            () => {
                                this.$router.push('/');
                            },
                            error => {
                                this.loading = false;
                                this.message =
                                    (error.response && error.response.data && error.response.data.message) ||
                                    error.message ||
                                    error.toString();
                            }
                        );
                    }
                });
            }
        }
    };
</script>

<style lang="css" scoped>

</style>