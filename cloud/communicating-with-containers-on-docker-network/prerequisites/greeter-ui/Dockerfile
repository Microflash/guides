# Generate a build
FROM node:12-alpine as builder
WORKDIR /app
COPY . /app
RUN yarn
RUN yarn build

# Serve with Nginx
FROM nginx:1.17-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY default.conf /etc/nginx/conf.d/
COPY --from=builder /app/dist/greeter-ui /usr/share/nginx/html
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]