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

# Expose port 4200
EXPOSE 4200

# Start the Angular development server
CMD ["ng", "serve", "--host", "192.168.33.27", "--port", "4200"]
