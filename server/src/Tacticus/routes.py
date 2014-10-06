from Tacticus import app
from flask import Flask, url_for, render_template

@app.route('/')
def index():
    return "Hello World!"