## Build Stage
FROM node:18.13 AS build

RUN mkdir -p /usr/src/app

# Set the working directory to the app directory
WORKDIR /usr/src/app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install Angular CLI globally (you can also install it locally)
RUN npm install -g @angular/cli

# Install project dependencies
RUN npm ci

# Copy the rest of the application source code
COPY . .

# Build the Angular application
RUN ng build --configuration=production --output-path=dist

## Production Stage
FROM nginx:1.21-alpine as production-stage

## Copy the custom NGINX configuration file into /etc/nginx/conf.d/
COPY nginx.conf /etc/nginx/conf.d/default.conf

## Copy the built files from the previous stage into the NGINX server
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 4200
EXPOSE 80

# Start the Angular development server
CMD ["nginx", "-g", "daemon off;"]
