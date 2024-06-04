import {css, html, LitElement} from 'lit';
import '@vaadin/progress-bar';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column.js';
import '@vaadin/button';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/split-layout';
import '@vaadin/details';
import '@vaadin/upload';
import '@vaadin/message-input';
import '@vaadin/message-list';

export class TicChatbot extends LitElement {
    static styles = css`

        :host {
            display: flex;
            gap: 10px;
            width: 80%;
            height: 80%;
            margin: auto;
            justify-content: start;
            flex-direction: column;
            background: ghostwhite;
            overflow: auto;
        }

        .hidden {
            visibility: hidden;
        }

        .show {
            visibility: visible;
        }

       
    `;

    static properties = {
        _chatItems: {state: true},
        _progressBarClass: {state: true},
    }

    constructor() {
        super();
        this._chatItems = [];
        this._progressBarClass = "hidden";
        this.ws = null;
    }

    connectedCallback() {
        super.connectedCallback();
        this.ws = new WebSocket("ws://localhost:8080/chat");
        this.ws.addEventListener("message", (event) => {
            console.log("Message from server ", event.data);
            this._hideProgressBar();
            this._chatItems = [
                ...this._chatItems,
                {
                    text: event.data,
                    userName: "Mona",
                    userColorIndex: 1
                }];
            this.requestUpdate();
        })
    }

    disconnectedCallback() {
        super.disconnectedCallback();
    }


    render() {
        if (this._chatItems) {
            return html`${this._renderChat()}`;
        } else {
            return html`Loading claims
            <vaadin-progress-bar indeterminate></vaadin-progress-bar>`;
        }
    }

    _renderChat() {
        return html`
            <div class="chat">
                <vaadin-message-list .items="${this._chatItems}"></vaadin-message-list>
                <vaadin-progress-bar class="${this._progressBarClass}" indeterminate></vaadin-progress-bar>
                <vaadin-message-input @submit="${this._handleSendChat}"></vaadin-message-input>
            </div>`;
    }

    _addBotMessage(message) {
        this._addMessage(message, "AI", 3);
    }

    _addUserMessage(message) {
        this._addMessage(message, "Me", 1);
    }

    _addStyledMessage(message, user, colorIndex, className) {
        let newItem = this._createNewItem(message, user, colorIndex);
        newItem.className = className;
        this._addMessageItem(newItem);
    }

    _addMessage(message, user, colorIndex) {
        this._addMessageItem(this._createNewItem(message, user, colorIndex));
    }

    _hideProgressBar() {
        this._progressBarClass = "hidden";
    }

    _showProgressBar() {
        this._progressBarClass = "show";
    }

    _handleSendChat(e) {
        let message = e.detail.value;
        if (message && message.trim().length > 0) {
            this._chatItems = [
                ...this._chatItems,
                {
                    text: message,
                    userName: "Me",
                    userColorIndex: 0
                }]
            this.requestUpdate();
            this._showProgressBar();
            this.ws.send(message);
        }

    }
}

customElements.define('tic-chatbot', TicChatbot);