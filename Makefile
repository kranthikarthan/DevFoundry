SHELL := /bin/bash

.PHONY: install dev build test docker-build up down

install:
	npm install

dev:
	npm run dev

build:
	npm run build

test:
	npm test

docker-build:
	docker build -t devportal-api ./api
	docker build -t devportal-web ./web

up:
	docker compose up --build

down:
	docker compose down
