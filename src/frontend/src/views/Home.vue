<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app :style="{background: $vuetify.theme.themes.dark.background}">
        <v-container fluid >
            <v-row>
                <v-col cols="12" sm="3" class="border">

                    <v-app-bar
                            color="deep-purple"
                            dark
                    >
                        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

                        <v-toolbar-title>Меню</v-toolbar-title>
                    </v-app-bar>
                    <v-navigation-drawer
                            v-model="drawer"
                            absolute
                            temporary
                            width="25%"
                    >
                        <template v-slot:prepend>
                            <v-list-item two-line>
                                <v-list-item-avatar>
                                    <img src="https://randomuser.me/api/portraits/women/81.jpg">
                                </v-list-item-avatar>

                                <v-list-item-content>
                                    <v-list-item-title>Владислав</v-list-item-title>
                                    <v-list-item-subtitle>@dudos</v-list-item-subtitle>
                                </v-list-item-content>
                            </v-list-item>
                        </template>

                        <v-divider></v-divider>

                        <v-list dense>
                            <v-list-item
                            >
                                <v-btn @click="logOut">Выйти</v-btn>
                            </v-list-item>
                        </v-list>
                    </v-navigation-drawer>
                    <v-app-bar flat color="rgba(0,0,0,0)">
                        <v-toolbar-title class="title">
                            Чат
                        </v-toolbar-title>

                        <v-spacer></v-spacer>
                    </v-app-bar>
                    <v-app-bar flat color="rgba(0,0,0,0)">

                        <v-text-field
                                filled
                                label="Поиск..."
                                append-icon="mdi-magnify"
                                color="grey"
                        ></v-text-field>
                    </v-app-bar>

                    <v-list two-line color="rgba(0,0,0,0)">
                        <v-list-item-group
                                v-model="selected"
                                active-class="blue lighten-4"
                                multiple
                        >
                            <template v-for="(item, index) in items">
                                <v-list-item :key="item.title">
                                    <v-badge
                                            bordered
                                            bottom
                                            color="green"
                                            dot
                                            offset-x="22"
                                            offset-y="26"
                                    >
                                        <v-list-item-avatar>
                                            <v-img :src="item.avatar"></v-img>
                                        </v-list-item-avatar>
                                    </v-badge>
                                    <template >
                                        <v-list-item-content>
                                            <v-list-item-title v-text="item.title"></v-list-item-title>

                                            <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
                                        </v-list-item-content>
                                    </template>
                                </v-list-item>

                                <v-divider
                                        v-if="index < items.length - 1"
                                        :key="index"
                                ></v-divider>
                            </template>
                        </v-list-item-group>
                    </v-list>

                </v-col>
                <v-col cols="12" sm="9" class="border">
                    <v-app-bar color="rgba(0,0,0,0)" flat>
                        <v-badge
                                bordered
                                bottom
                                color="green"
                                dot
                                offset-x="11"
                                offset-y="13"
                        >
                            <v-avatar class="mt-n7" size="40" elevation="10">
                                <img src="https://cdn.vuetifyjs.com/images/lists/1.jpg" />
                            </v-avatar>
                        </v-badge>
                        <v-toolbar-title class="title pl-0 ml-2 mt-n4">
                            JavaSenior
                        </v-toolbar-title>
                        <v-spacer></v-spacer>
                    </v-app-bar>
                    <v-virtual-scroll
                            :items="myMessage"
                            :item-height="250"
                            height="300"
                    >
                        <template v-slot:default="{ msg }">
                            <!--Если я отправил who=1 - от меня сообщение-->
                            <v-app-bar color="rgba(0,0,0,0)" flat class="mb-16">

                                <v-spacer></v-spacer>
                                <v-card class="mt-10 mr-2" max-width="350px" color="blue" dark>
                                    <v-list-item three-line>
                                        <v-list-item-content>
                                            <div class=" mb-4">
                                                {{ msg.text }}
                                            </div>
                                            <v-list-item-subtitle> {{ msg.when }} <span class="ml-16">Просмотрено</span></v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-card >
                            </v-app-bar>

                            <!--Если я отправил who=0 - сообщение мне-->
                            <v-app-bar color="rgba(0,0,0,0)" flat class="mb-16">
                                <v-spacer></v-spacer>
                                <v-card class="mt-10 " max-width="350px">
                                    <v-list-item three-line>
                                        <v-list-item-content>
                                            <div class=" mb-4">
                                                {{ msg.text }}
                                            </div>
                                            <v-list-item-subtitle>{{ msg.when }}</v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-card >
                                <v-btn
                                        color="black"
                                        icon
                                        class="mb-n10"
                                >
                                    <v-icon>fas fa-ellipsis-h</v-icon>
                                </v-btn>
                            </v-app-bar>

                        </template>
                    </v-virtual-scroll>


                    <v-app-bar color="rgba(0,0,0,0)" flat>
                        <v-text-field

                                v-model="message"
                                append-icon="mdi-emoticon"
                                :append-outer-icon="message ? 'mdi-send' : 'mdi-microphone'"
                                filled
                                clear-icon="mdi-close-circle"
                                clearable
                                label="Сообщение"
                                type="text"
                                @click:append-outer="sendMessage"
                                @click:clear="clearMessage"
                        ></v-text-field>
                    </v-app-bar>
                </v-col>
            </v-row>
        </v-container>
    </v-app>
</template>
<script>
    export default {

        data: () => ({
            selected: [2],
            items: [
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
                    subtitle: `Нужно пофиксить баг`,
                    title: 'JavaSenior',
                },
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
                    subtitle: `Люблю Романовых`,
                    title: 'Распутин',
                },
                {
                    avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
                    subtitle: 'Вам перечислено 100 млрд.евро',
                    title: 'Visa',
                }
            ],
            files: [
                { text: 'Landing_page.zip', icon: ' mdi-cloud-upload' },
                { text: 'Requirements.pdf', icon: ' mdi-cloud-upload' },
                { text: 'Uwagi.docx', icon: ' mdi-cloud-upload' },
            ],
            panel: [2],

            messages:
                [
                    { text: 'Home', who: 1, when: "24.01.2022" },
                    { text: 'My Account', who: -1,when: "24.01.2022" },
                    { text: 'Users', who: 1,when: "24.01.2022" },
                ],
            password: 'Password',
            show: false,
            message: 'Введите свое сообщение здесь',
            marker: true,
            iconIndex: 0,
            //для toggle-navigation-drawers
            drawer: false,
            group: null


        }),
        computed: {
            theme() {
                return this.$vuetify.theme.dark ? "dark" : "light";
            },
            myMessage () {

                return Array.from({ length: 2 }, () => {
                    let whoSender = "-1";
                    console.log(whoSender);
                    return {
                        text:'${this.messages[v].text}',
                        when:'${this.messages[v].when}',
                        who: '${whoSender}'
                    }
                })
            }

        },
        methods: {
            sendMessage () {
                this.resetIcon()
                this.clearMessage()
            },
            clearMessage () {
                this.message = ''
            },
            resetIcon () {
                this.iconIndex = 0
            },
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        },

    };
</script>
<style scoped>
    .border {
        border-right: 1px solid grey;
    }
</style>
