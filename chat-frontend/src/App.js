import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import MessageList from "./MessageList";
import MessageInput from "./MessageInput";

function App() {
    localStorage.setItem("userName", "Alexander")
    localStorage.setItem("publicKey", "AlexanderPublicKey")
    localStorage.setItem("privateKey", "AlexanderPrivateKey")
  return (
    <div className="App">
        <MessageList companionKey="AndrewPublicKey"/>
        <MessageInput recipientKey="AndrewPublicKey" recipientName="Andrew"/>
    </div>
  );
}

export default App;
