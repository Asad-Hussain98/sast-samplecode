FROM python:3.10
LABEL author='Asad Hussain'
LABEL application='User Mangement Service 2.0'

WORKDIR /app

COPY requirements.txt /app/requirements.txt
RUN pip install -r /app/requirements.txt
COPY run.py /app
COPY app /app/app

ENV DATABASE=user_service
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV DB_HOST=localhost
ENV DB_PORT=3306

ENV DEBUG=False
ENV SECRET=secret*Pompie
ENV SET_LOGGER=True

EXPOSE 5000
ENTRYPOINT [ "python" ]
CMD [ "/app/app/app.py" ]

# --------------------------
# Items does not found in database 404
# Database connection error 503
# Invalid body parameters 406
# InsertError('Unable to add role in database') 422
# EntityAlreadyExists 409 Conflits
# UpdateError 422
# Invitation has expired 410
