<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app :style="{background: $vuetify.theme.themes.dark.background}">
        <v-container fluid>
            <v-row>
                <v-col cols="12" sm="3" class="border">

                    <v-app-bar
                            color="deep-purple"
                            dark
                    >
                        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

                        <v-toolbar-title>Меню</v-toolbar-title>
                        <img src="/avatars/cat.png"/>
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
                    <v-col
                            cols="auto"
                            class="flex-grow-1 flex-shrink-0"
                    >
                        <v-responsive
                                class="overflow-y-hidden fill-height"
                                height="500"
                        >
                            <v-card
                                    flat
                                    class="d-flex flex-column fill-height"
                            >
                                <v-card-text class="flex-grow-1 overflow-y-auto">
                                    <template >
                                        <div v-for="(msg) in messages" :key="msg" >
                                        <div
                                                :class="{ 'd-flex flex-row-reverse': msg.me }"
                                                style="text-align: initial"
                                        >
                                            <v-menu offset-y>
                                                <template v-slot:activator="{ on }">
                                                    <v-hover >
                                                        <v-chip
                                                                :color="msg.me ? 'primary' : ''"
                                                                dark
                                                                style="height:auto;white-space: normal; text-align: start"
                                                                class="pa-4 mb-8"
                                                                v-on="on"
                                                        >
                                                            {{ msg.text }}
                                                            <sub
                                                                    class="ml-2"
                                                                    style="font-size: 0.5rem;"
                                                            >{{ msg.when }}</sub>
                                                        </v-chip>
                                                    </v-hover>
                                                </template>
                                                <v-list>
                                                    <v-list-item>
                                                        <v-list-item-title>delete</v-list-item-title>
                                                    </v-list-item>
                                                </v-list>
                                            </v-menu>
                                        </div>
                                        </div>
                                    </template>
                                </v-card-text>
                            </v-card>
                        </v-responsive>
                    </v-col>


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
    import Vue from 'vue';

    var app5 = new Vue({
        data: {
            messages: [
                {
                    text: 'test',
                    me: false,
                    when: ''
                }
            ]
        },
        created(){
            // send a request to get result, and assign the value to a, b, c here

        },
    });
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
                    { text: 'Home', me: true, when: "24.01.2022" },
                    { text: 'My Account', me: false,when: "24.01.2022" },
                    { text: 'Users', me: true,when: "24.01.2022" },
                    { text: 'Home', me: true, when: "24.01.2022" },
                    { text: 'My Account', me: false,when: "24.01.2022" },
                    { text: 'Users', me: true,when: "24.01.2022" },
                    { text: 'Home', me: true, when: "24.01.2022" },
                    { text: 'My Account', me: false,when: "24.01.2022" },
                    { text: 'Users', me: true,when: "24.01.2022" },
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
//item,index
                return Array.from({ length: 2 }, (v,k) => {

                    let whoSender = false;
                    app5.messages.push(this.messages[k]);
                    console.log(app5.messages);
                    //console.log(vm.$messages);
                    return {
                        text:app5.messages[1].text,
                        when:app5.messages[1].when,
                        who: whoSender
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
                this.messages.push({ text: this.message, me: true, when: new Date().toLocaleString() });
                console.log(this.messages)

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
