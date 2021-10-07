import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import MessageList from "./MessageList";
import MessageInput from "./MessageInput";
import SignIn from "./SignIn";

function App() {
  return (
    <div className="App">
        <SignIn/>
        <MessageList companionKey="AndrewPublicKey"/>
        <MessageInput recipientKey="AndrewPublicKey" recipientName="Andrew"/>
    </div>
  );
}

export default App;
