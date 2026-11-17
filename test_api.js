// Test script to verify user creation API
// Run this in browser console while on the admin dashboard

async function testUserCreation() {
    console.log('Testing user creation API...');
    
    const testUser = {
        firstName: 'API',
        lastName: 'Test',
        email: 'api.test@example.com',
        phoneNumber: '555-8888',
        password: '<CHANGE_ME>',
        role: 'CUSTOMER'
    };
    
    try {
        const response = await fetch('/system-admin/api/admin/users', {
            method: 'POST',
            <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(testUser)
        });
        
        const data = await response.json();
        
        if (data.success) {
            console.log('✅ User created successfully!', data);
            
            // Test if user appears in the list
            const usersResponse = await fetch('/system-admin/api/admin/users');
            const usersData = await usersResponse.json();
            
            if (usersData.success) {
                const createdUser = usersData.users.find(user => user.email === testUser.email);
                if (createdUser) {
                    console.log('✅ User found in user list!', createdUser);
                } else {
                    console.log('❌ User not found in user list');
                }
            }
        } else {
            console.log('❌ User creation failed:', data.message);
        }
    } catch (error) {
        console.log('❌ Error:', error);
    }
}

// Test user statistics
async function testUserStatistics() {
    console.log('Testing user statistics API...');
    
    try {
        const response = await fetch('/system-admin/api/admin/user-statistics');
        const data = await response.json();
        
        if (data.success) {
            console.log('✅ User statistics retrieved:', data);
        } else {
            console.log('❌ Failed to get user statistics:', data.message);
        }
    } catch (error) {
        console.log('❌ Error:', error);
    }
}

// Test user search
async function testUserSearch() {
    console.log('Testing user search API...');
    
    try {
        const response = await fetch('/system-admin/api/admin/users/search?query=admin');
        const data = await response.json();
        
        if (data.success) {
            console.log('✅ User search successful:', data);
        } else {
            console.log('❌ User search failed:', data.message);
        }
    } catch (error) {
        console.log('❌ Error:', error);
    }
}

// Run all tests
async function runAllTests() {
    console.log('🚀 Starting all API tests...');
    await testUserStatistics();
    await testUserSearch();
    await testUserCreation();
    console.log('🏁 All tests completed!');
}

// Instructions
console.log(`
📋 API Testing Instructions:
1. Make sure you're logged in as admin
2. Run: testUserCreation() - Test creating a new user
3. Run: testUserStatistics() - Test getting user statistics  
4. Run: testUserSearch() - Test searching users
5. Run: runAllTests() - Run all tests at once

🔍 Check the console output for ✅ (success) or ❌ (error) indicators
`);
