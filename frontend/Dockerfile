#This is an Example Dockerfile which can be used for building an docker image of the application
#needs to be tested and adjusted
#------------------- 
# Use an official Node.js runtime as a parent image
FROM node:20

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port on which the app will run
EXPOSE 8080

# Define the command to run the application
CMD ["npm", "run", "dev"]
