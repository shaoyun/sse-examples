import json
import time

from flask import Flask, Response, stream_with_context

app = Flask(__name__)

@app.route('/stream')
def stream_data():
    msg = ['SSE','empowering','GPT','applications','!','Happy','chatting','!']
    # 可以使用 yield 逐字返回内容
    def generate_response_data():
        for i,word in enumerate(msg):
            json_data = json.dumps(
                {'id': i, 'content': word})
            yield f"data:{json_data}\n\n"
            time.sleep(1)
    return Response(stream_with_context(generate_response_data()), mimetype="text/event-stream")

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9000)
